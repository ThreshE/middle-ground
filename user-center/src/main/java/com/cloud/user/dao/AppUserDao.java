package com.cloud.user.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.cloud.model.user.AppUser;

@Mapper
public interface AppUserDao extends BaseMapper<AppUser> {

	@Select("select * from app_user t where t.username = #{username}")
	AppUser findByUsername(String username);

	int count(Map<String, Object> params);

	List<AppUser> findData(Map<String, Object> params);

}
