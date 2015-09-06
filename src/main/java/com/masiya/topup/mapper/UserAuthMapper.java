package com.masiya.topup.mapper;

import org.apache.ibatis.annotations.Param;

import com.masiya.topup.model.UserAuth;

public interface UserAuthMapper extends _IMapper<UserAuth, Integer> {
	public UserAuth getByLoginPwd(@Param("login") String login, @Param("pwd") String pwd);
	public UserAuth getByToken(@Param("token") String token);
}
