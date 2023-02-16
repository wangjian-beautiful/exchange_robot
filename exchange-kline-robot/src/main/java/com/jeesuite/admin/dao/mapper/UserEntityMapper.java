package com.jeesuite.admin.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.jeesuite.admin.dao.CustomBaseMapper;
import com.jeesuite.admin.dao.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public interface UserEntityMapper extends CustomBaseMapper<UserEntity> {
	
	@Select("SELECT * FROM users where name=#{name}")
	@ResultMap("BaseResultMap")
	UserEntity findByName(String name);
	
	
	@Select("SELECT * FROM users WHERE type <> 1 AND status <> 2")
	@ResultMap("BaseResultMap")
	List<UserEntity> findAllUser();
}
