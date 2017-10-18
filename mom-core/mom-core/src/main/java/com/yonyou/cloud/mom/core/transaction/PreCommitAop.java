package com.yonyou.cloud.mom.core.transaction;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yonyou.cloud.mom.core.transaction.executor.PreCommitExecutorDefaultImpl;
import com.yonyou.cloud.mom.core.transaction.executor.TransactionExecutor;

/**
 * 事务拦截器，在事务提交前做
 */
@Aspect
@Component
public class PreCommitAop {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreCommitAop.class);

    private final TransactionExecutor beforeCommitExecutor = new  PreCommitExecutorDefaultImpl();

    @Around(value = "@annotation(com.yonyou.cloud.mom.core.transaction.PreCommit)", argNames = "pjp")
    public Object aroundAdvice(final ProceedingJoinPoint pjp) {
        beforeCommitExecutor.execute(new AopCommitRunnable(pjp));
        return null;
    }

}
