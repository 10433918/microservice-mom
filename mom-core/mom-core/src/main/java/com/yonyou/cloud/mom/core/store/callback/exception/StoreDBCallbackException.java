package com.yonyou.cloud.mom.core.store.callback.exception;

/**
 * @author BENJAMIN
 * 
 * 存储DB回调的异常
 *
 */
public class StoreDBCallbackException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public StoreDBCallbackException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public StoreDBCallbackException(String exceptionMessage, Throwable throwable) {

        super(exceptionMessage, throwable);
    }
}
