package com.yonyou.cloud.mom.core.store.callback;

import com.yonyou.cloud.mom.core.store.callback.exception.StoreDBCallbackException;

/**
 * @author BENJAMIN
 * 存储DB的回调接口
 */
public interface ProducerStoreDBCallback{


    /**
     * @param correlation
     * @param data
     */
    void saveStatusData(String correlation, String data, String exchange, String routerKey)
        throws StoreDBCallbackException;

    /**
     * 处理为初始化失败
     *
     * @param correlation
     * @param infoMsg
     *
     * @throws StoreUserCallbackException
     */
    void update2InitFailed(String correlation, String infoMsg, Long costTime) throws StoreDBCallbackException;

}
