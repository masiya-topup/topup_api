package com.masiya.topup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.masiya.topup.model.User;
import com.masiya.topup.model.UserPhone;

public interface UserPhoneMapper extends _IMapper<UserPhone, Integer> {
	public List<UserPhone> getAllByUser(User user);

	public UserPhone getByNumber(@Param("number") String phoneNumber);
	public UserPhone getByPhone(@Param("number") String phoneNumber, @Param("type") String phoneType);
}
