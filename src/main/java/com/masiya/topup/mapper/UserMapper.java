package com.masiya.topup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.masiya.topup.model.User;

public interface UserMapper extends _IMapper<User, Integer> {
	public List<User> getAllByRole(@Param("role") String role);
	public List<User> getAllByRoleCondition(@Param("role") String role, @Param("search") String search);
	public User getByEMail(@Param("email") String email);
	public User getByLogin(@Param("login") String login);
}
