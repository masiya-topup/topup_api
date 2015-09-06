package com.masiya.topup.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.masiya.topup.model.Transaction;

public interface TransactionMapper extends _IMapper<Transaction, Integer> {
	List<Transaction> getSearchDate(@Param("search") String search, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
