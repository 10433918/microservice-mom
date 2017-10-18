package com.yonyou.cloud.mom.core.store.callback.exception;

/**
 * @author BENJAMIN
 * 存储的异常
 */
public class StoreException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public StoreException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public StoreException(String exceptionMessage, Throwable throwable) {

        super(exceptionMessage, throwable);
    }
}
