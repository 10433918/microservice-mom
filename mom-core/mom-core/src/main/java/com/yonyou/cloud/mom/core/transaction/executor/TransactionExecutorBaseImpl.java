package com.yonyou.cloud.mom.core.transaction.executor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 事务提交的base
 * 
 * @author BENJAMIN
 *
 */
public class TransactionExecutorBaseImpl extends TransactionSynchronizationAdapter implements TransactionExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionExecutorBaseImpl.class);
    protected final ThreadLocal<List<Runnable>> RUNNABLES = new ThreadLocal<List<Runnable>>();
    
    
	/**
	 * 注册到事务处理器
	 */
    @Override
	public void execute(Runnable command) {
		  LOGGER.debug("Submitting new runnable {} to run around commit", command);

	        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
	            LOGGER.debug("事务同步未激活. 直接开始执行线程 {}", command);
	            command.run();
	            return;
	        }
	        List<Runnable> threadRunnables = RUNNABLES.get();
	        if (threadRunnables == null) {
	            threadRunnables = new ArrayList<Runnable>();
	            RUNNABLES.set(threadRunnables);
	            TransactionSynchronizationManager.registerSynchronization(this);
	        }
	        threadRunnables.add(command);
	}

}
