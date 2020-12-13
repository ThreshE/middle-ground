package com.cloud.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cloud.model.user.AppUser;
import com.cloud.model.user.UserCredential;

@Mapper
public interface UserCredentialsDao extends BaseMapper<UserCredential> {

	@Select("select * from user_credentials t where t.username = #{username}")
	UserCredential findByUsername(String username);

	@Select("select u.* from app_user u inner join user_credentials c on c.user_id = u.id where c.username = #{username}")
	AppUser findUserByUsername(String username);
}
