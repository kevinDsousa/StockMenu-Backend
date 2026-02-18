package com.main.infrastructure.parallel;

import java.time.Duration;
import java.util.concurrent.Callable;

public record ParallelTask<T>(
        String name,
        Callable<T> callable,
        Duration timeout,
        int retries
) {}