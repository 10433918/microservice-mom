package com.yonyou.cloud.mom.client;


public interface MqSender {
	
	/**
	 * 一致性发送：不会丢失消息
	 * 
	 * @param exchange
	 * @param routeKey
	 * @param data
	 */
	void send(String exchange, String routeKey, Object data);
	
	/**
	 * 非一致性发送：极端情况有可能会丢失消息
	 * 
	 * @param exchange
	 * @param routeKey
	 * @param data
	 */
	void directSend(String exchange, String routeKey, Object data);
}
