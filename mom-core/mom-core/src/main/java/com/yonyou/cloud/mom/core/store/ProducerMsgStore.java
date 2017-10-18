package com.yonyou.cloud.mom.core.store;

import com.yonyou.cloud.mom.core.store.callback.exception.StoreException;

/**
 * @author BENJAMIN
 * 
 *
 */
public interface ProducerMsgStore {

    void msgStore(String msgKey, String data, String exchange, String routerKey) throws StoreException;

    /**
     * 处理为失败
     *
     * @param correlation
     * @param infoMsg
     *
     * @throws StoreException
     */
    void msgStoreFailed(String msgKey, String infoMsg, Long costTime) throws StoreException;
}