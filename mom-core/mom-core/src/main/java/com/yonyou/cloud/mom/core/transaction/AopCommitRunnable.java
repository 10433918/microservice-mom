package com.yonyou.cloud.mom.core.transaction;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 执行事务提交线程
 * 
 * @author BENJAMIN
 *
 */
public class AopCommitRunnable implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopCommitRunnable.class);

    private final ProceedingJoinPoint pjp;

    public AopCommitRunnable(ProceedingJoinPoint pjp) {
        this.pjp = pjp;
    }

    @Override
    public void run() {
        try {
            pjp.proceed();
        } catch (Throwable e) {
            LOGGER.error("Exception while invoking pjp.proceed()", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        String typeName = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();
        return "PjpCommitRunnable[type=" + typeName + ", method=" + methodName + "]";
    }
}