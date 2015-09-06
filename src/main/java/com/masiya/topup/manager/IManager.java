package com.masiya.topup.manager;

import java.util.List;

public interface IManager<T, PK> {

	public T add(T elem);
	public T update(T elem);
	public boolean delete(PK id);
	
	public List<T> selectAll(String search);
	public T selectById(PK id);
}
