package com.masiya.topup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface _IMapper<T, PK> {
	public List<T> getAll(@Param("search") String search);
	public T getById(PK id);
	
	public void add(T elem);
	public void update(T elem);
	public boolean delete(PK id);
}
