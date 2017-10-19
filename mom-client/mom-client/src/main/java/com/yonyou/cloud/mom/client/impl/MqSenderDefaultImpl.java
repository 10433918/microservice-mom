package com.yonyou.cloud.mom.client.impl;

import java.io.IOException;
import java.util.UUID;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitGatewaySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.mom.client.MqSender;
import com.yonyou.cloud.mom.core.store.ProducerMsgStore;
import com.yonyou.cloud.mom.core.transaction.executor.AfterCommitExecutorDefaultImpl;
import com.yonyou.cloud.mom.core.transaction.executor.PreCommitExecutorDefaultImpl;
import com.yonyou.cloud.mom.core.transaction.executor.TransactionExecutor;

/**
 * 发送mq的默认实现
 * 
 * @author BENJAMIN
 *
 */
public class MqSenderDefaultImpl extends RabbitGatewaySupport implements MqSender {

	protected final Logger LOGGER = LoggerFactory.getLogger(MqSenderDefaultImpl.class);

	// 事务提交前的执行类
	private TransactionExecutor beforeCommitExecutor = new PreCommitExecutorDefaultImpl();

	// 事务提交后的执行类
	private TransactionExecutor afterCommitExecutor = new AfterCommitExecutorDefaultImpl();

	// msg Db Store的实现
	@Autowired
	private ProducerMsgStore msgStore ;//= new DbStoreProducerMsg();

	@Override
	@Transactional
	public void send(String exchange, String routeKey, Object data) {
		// 消息主键
		String msgKey;
		msgKey = UUID.randomUUID().toString();

		ObjectMapper mapper = new ObjectMapper();
		// 转换后的String
		String dataConvert;
		try {
			// convert
			dataConvert = mapper.writeValueAsString(data);

			// store
			storeMsg(dataConvert, msgKey, exchange, routeKey);

		} catch (IOException e) {
			throw new AmqpException(e);
		}

		sendToMQ(exchange, routeKey, msgKey, data);

	}

	@Override
	public void directSend(String exchange, String routeKey, Object data) {
		// TODO Auto-generated method stub

	}

	private void sendToMQ(final String exchange, final String routeKey, final String msgKey, final Object data) {
		afterCommitExecutor.execute(new Runnable() {
			@Override
			public void run() {

				Long startTime = 0L;
				try {
					LOGGER.debug("------发送消息开始------");
					startTime = System.currentTimeMillis();
					sendRabbitQ(exchange,routeKey, msgKey, data);

				} catch (Exception e) {
					// 设置为失败
					LOGGER.debug("------发送消息异常，调用消息存储失败的方法------");
					msgStore.msgStoreFailed(msgKey, e.toString(), System.currentTimeMillis() - startTime);
				}
			}
		});
	}
	
	
	protected void sendRabbitQ(final String exchange,final String routeKey, final String correlation, final Object data) {

		getRabbitOperations().convertAndSend(exchange,routeKey, data, new MessagePostProcessor() {

			public Message postProcessMessage(Message message) throws AmqpException {

                try {

                    message.getMessageProperties().setCorrelationIdString(correlation);

                } catch (Exception e) {
                    throw new AmqpException(e);
                }

                return message;
            }
        });

    }

	/**
	 * 存储消息
	 * 
	 * @param data
	 * @param msgKey
	 * @param exchange
	 * @param routerKey
	 * @throws IOException
	 */
	private void storeMsg(final String data, final String msgKey, final String exchange, final String routerKey)
			throws IOException {

		// save data before commit
		beforeCommitExecutor.execute(new Runnable() {
			@Override
			public void run() {
				msgStore.msgStore(msgKey, data, exchange, routerKey);
			}
		});
	}

}
