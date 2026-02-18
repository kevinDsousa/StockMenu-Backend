package com.main.infrastructure.exeptions;

import java.io.Serial;

public class ParallelExecutionException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7812349871234987123L;

    public ParallelExecutionException(String message) {
        super(message);
    }

    public ParallelExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}