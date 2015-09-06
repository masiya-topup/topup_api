package com.masiya.topup.mapper;

import org.apache.ibatis.annotations.Param;

import com.masiya.topup.model.PasswordReset;

public interface PasswordResetMapper extends _IMapper<PasswordReset, Integer> {
	public PasswordReset getByHash(@Param("hashKey") String hashKey);
}
