package com.yonyou.cloud.mom.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yonyou.cloud.mom.demo.msg.entity.BizEntity;

public interface BizDao extends JpaRepository<BizEntity, String>{

}
