package com.yonyou.cloud.mom.demo.msg.callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.mom.core.store.callback.ProducerStoreDBCallback;
import com.yonyou.cloud.mom.core.store.callback.exception.StoreDBCallbackException;
import com.yonyou.cloud.mom.demo.dao.MsgDao;
import com.yonyou.cloud.mom.demo.msg.entity.MsgEntity;

@Service
public class DemoMsgCallBack implements ProducerStoreDBCallback{

	
	@Autowired
	MsgDao msgDao;
	
	
	@Override
	public void saveStatusData(String correlation, String data, String exchange, String routerKey)
			throws StoreDBCallbackException {
		System.out.println("进入存储消息的逻辑"+correlation);
		MsgEntity msg = new MsgEntity();
		msg.setId(correlation);
		msg.setMsg(data);
		msg.setIsSend("0");
		msgDao.save(msg);
		
	}

	@Override
//	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
	public void update2InitFailed(String correlation, String infoMsg, Long costTime) throws StoreDBCallbackException {
		System.out.println("进入消息发送失败的逻辑"+correlation);
		MsgEntity msg = new MsgEntity();
		msg.setId(correlation);
		msg.setIsSend("1");
//		msgDao.findAll();
		msgDao.save(msg);
		System.out.println("===========wtffffff");
		
	}

}
