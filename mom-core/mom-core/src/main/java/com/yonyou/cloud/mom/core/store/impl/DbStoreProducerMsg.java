package com.yonyou.cloud.mom.core.store.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.mom.core.store.ProducerMsgStore;
import com.yonyou.cloud.mom.core.store.callback.ProducerStoreDBCallback;
import com.yonyou.cloud.mom.core.store.callback.exception.StoreException;
import com.yonyou.cloud.mom.core.util.SpringUtil;

/**
 * 存储生产者消息的实现
 * 通过DB存储
 */
@Transactional
public class DbStoreProducerMsg implements ProducerMsgStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbStoreProducerMsg.class);

    /**
     * 存储消息
     */
    @Override
    public void msgStore(String msgKey, String data, String exchange, String routerKey) throws StoreException {

    	ProducerStoreDBCallback producerStoreDBCallback =
            (ProducerStoreDBCallback) SpringUtil.getBean(ProducerStoreDBCallback.class);
    	
        if (producerStoreDBCallback != null && data != null && msgKey != null) {
            LOGGER.debug("save msg to db.");
            producerStoreDBCallback.saveStatusData(msgKey, data, exchange, routerKey);

        } else {

            String errorMsg = "";
            if (producerStoreDBCallback == null) {
                errorMsg = "producerStoreDBCallback is null";
            } else if (data == null) {
                errorMsg = "data is null";
            } else {
                errorMsg = "msgKey is null";
            }

            LOGGER.error("msgKey is null");
            throw new StoreException(errorMsg);
        }
    }

    /**
     * 处理为失败
     *
     * @param msgKey
     * @param infoMsg
     * @param costTime
     *
     * @throws StoreException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor =RuntimeException.class)
    public void msgStoreFailed(String msgKey, String infoMsg, Long costTime) throws StoreException {

    	ProducerStoreDBCallback producerStoreDBCallback =
            (ProducerStoreDBCallback) SpringUtil.getBean(ProducerStoreDBCallback.class);

        if (producerStoreDBCallback != null && msgKey != null) {

            LOGGER.debug("data encounter error: " + infoMsg);
            producerStoreDBCallback.update2InitFailed(msgKey, infoMsg, costTime);

        } else {

            String errorMsg = "";
            if (producerStoreDBCallback == null) {
                errorMsg = "dbStoreUserCallback is null";
            } else {
                errorMsg = "msgKey is null";
            }

            LOGGER.error("msgKey is null");
            throw new StoreException(errorMsg);
        }
    }
}
