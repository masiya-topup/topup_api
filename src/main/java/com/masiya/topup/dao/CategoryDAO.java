package com.masiya.topup.dao;

import com.masiya.topup.mapper.CategoryMapper;
import com.masiya.topup.model.Category;

public class CategoryDAO extends _GenericDAO<Category, Integer, CategoryMapper> {

	public CategoryDAO() {
		super(CategoryMapper.class);
	}

}
