package com.yonyou.cloud.mom.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.mom.demo.msg.entity.MsgEntity;

public interface MsgDao extends JpaRepository<MsgEntity, String>{
	

}
