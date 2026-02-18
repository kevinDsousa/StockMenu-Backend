package com.main.infrastructure.parallel;

import com.main.infrastructure.exeptions.ParallelExecutionException;
import com.main.infrastructure.parallel.enums.ParallelStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TaskRunner {

    private static final Logger log = LoggerFactory.getLogger(TaskRunner.class);

    private static final ExecutorService executor =
            Executors.newVirtualThreadPerTaskExecutor();


    /**
     * Execute resilient t.
     *
     * @param <T>           the type parameter
     * @param taskName      the task name
     * @param task          the task
     * @param timeout       the timeout
     * @param retryAttempts the retry attempts
     * @return the t
     */
    public static <T> T executeResilient(String taskName, Callable<T> task, Duration timeout, int retryAttempts) {

        int attempts = 0;

        while (true) {
            attempts++;

            Instant start = Instant.now();
            log.info("Starting task [{}] attempt {}", taskName, attempts);

            Future<T> future = executor.submit(task);

            try {
                T result = future.get(timeout.toMillis(), TimeUnit.MILLISECONDS);

                long duration = Duration.between(start, Instant.now()).toMillis();
                log.info("Finished task [{}] in {} ms", taskName, duration);

                return result;

            } catch (TimeoutException e) {
                future.cancel(true);
                log.error("Timeout in task [{}]", taskName);

            } catch (Exception e) {
                log.error("Error in task [{}]: {}", taskName, e.getMessage(), e);
            }

            if (attempts >= retryAttempts) {
                throw new ParallelExecutionException("Task failed after retries: " + taskName);
            }

            log.warn("Retrying task [{}]...", taskName);
        }
    }


    /**
     * Execute in parallel with strategy list.
     *
     * @param <T>      the type parameter
     * @param tasks    the tasks
     * @param strategy the strategy
     * @return the list
     */
    public static <T> List<T> executeInParallelWithStrategy(List<ParallelTask<T>> tasks, ParallelStrategy strategy) {

        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        List<Future<T>> futures = new ArrayList<>();

        try {

            for (ParallelTask<T> task : tasks) {
                futures.add(executor.submit(() ->
                        executeResilient(task.name(), task.callable(), task.timeout(), task.retries())
                ));
            }

            List<T> results = new ArrayList<>();

            for (Future<T> future : futures) {
                try {
                    results.add(future.get());
                } catch (Exception e) {

                    if (strategy == ParallelStrategy.FAIL_FAST) {
                        log.error("Fail-fast triggered, cancelling all tasks");
                        futures.forEach(f -> f.cancel(true));
                        throw new ParallelExecutionException("Parallel execution failed", e);
                    }

                    if (strategy == ParallelStrategy.BEST_EFFORT) {
                        log.warn("Task failed but continuing execution");
                        results.add(null);
                    }
                }
            }

            return results;

        } finally {
            executor.shutdown();
        }
    }
}