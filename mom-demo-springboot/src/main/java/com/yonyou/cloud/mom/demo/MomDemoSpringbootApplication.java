package com.yonyou.cloud.mom.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.cloud.mom.demo.dao.MsgDao;
import com.yonyou.cloud.mom.demo.service.BizService;

@SpringBootApplication
@RestController
public class MomDemoSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MomDemoSpringbootApplication.class, args);
	}
	
	@Autowired
	private BizService bizService;
	
	@RequestMapping("/login")
	public String sendMsg(String name) throws Exception{
		return bizService.saveLoginUser(name);
	}
	
	
	@Autowired
	MsgDao msgDao;
	
	@RequestMapping("/test2")
	public List sendMsg2(){
		
		return msgDao.findAll();
		
	}
	
}
