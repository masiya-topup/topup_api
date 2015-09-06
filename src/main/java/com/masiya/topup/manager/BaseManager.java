package com.masiya.topup.manager;

import java.util.List;

public class BaseManager<T, PK> implements IManager<T, PK> {

	@Override
	public T add(T elem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T update(T elem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(PK id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<T> selectAll(String search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T selectById(PK id) {
		// TODO Auto-generated method stub
		return null;
	}

}
