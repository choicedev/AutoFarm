package com.choice.autofarm.util;

import org.mineacademy.fo.Common;

public class ExceptionUtils {

    public static void runCatching(RunnableWithException runnable, ExceptionHandler exceptionHandler, FinallyBlock finallyBlock) {
        try {
            runnable.run();
        } catch (Exception e) {
            Common.error(
                    e,
                    "Error: %error"
            );

            if(exceptionHandler != null) exceptionHandler.handleException(e);
        } finally {
            if(finallyBlock != null) finallyBlock.run();
        }
    }

    public interface RunnableWithException {
        void run() throws Exception;
    }

    public interface ExceptionHandler {
        void handleException(Exception e);
    }

    public interface FinallyBlock {
        void run();
    }
}
