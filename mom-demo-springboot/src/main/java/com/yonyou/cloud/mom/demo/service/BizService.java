package com.yonyou.cloud.mom.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.mom.client.MqSender;
import com.yonyou.cloud.mom.demo.dao.BizDao;
import com.yonyou.cloud.mom.demo.msg.entity.BizEntity;

@Service
@Transactional
public class BizService {
	
	
	@Autowired
	private MqSender mqSender;
	
	@Autowired
	private BizDao bizDao;
	
	public String saveLoginUser(String name) throws InterruptedException{
		
		BizEntity e = new BizEntity();
		e.setId(name);
		e.setName(name);
		bizDao.save(e);
		
		mqSender.send("tt", "login", name+"log event");
		Thread.sleep(10*1000);
		if(name.equals("liuxudong")){
			throw new RuntimeException();
		}
		return "1";
	}

}
