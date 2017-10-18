package com.yonyou.cloud.mom.core.transaction;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yonyou.cloud.mom.core.transaction.executor.AfterCommitExecutorDefaultImpl;
import com.yonyou.cloud.mom.core.transaction.executor.TransactionExecutor;

/**
 * 事务拦截器 在事务提交成功 来做
 */
@Aspect
@Component
public class AfterCommitAop {

    private static final Logger LOGGER = LoggerFactory.getLogger(AfterCommitAop.class);

    private final TransactionExecutor afterCommitExecutor = new AfterCommitExecutorDefaultImpl();

    @Around(value = "@annotation(com.yonyou.cloud.mom.core.transaction.AfterCommit)", argNames = "pjp")
    public Object aroundAdvice(final ProceedingJoinPoint pjp) {
    	System.out.println("in AfterCommitAop aroundAdvice");
        afterCommitExecutor.execute(new AopCommitRunnable(pjp));
        return null;
    }

}
