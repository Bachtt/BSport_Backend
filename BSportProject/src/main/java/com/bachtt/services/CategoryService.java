package com.bachtt.services;


import java.util.List;
import java.util.Optional;

import com.bachtt.models.Category;

public interface CategoryService {
	List<Category> findAllCategory();
	
	Optional<Category> findByCategoryId(Long categoryId);
	
	Category saveCategory(Category category);
	
	void removeCategory(Long categoryId);
	
	List<Category> findByCategoryName(String categoryName);
}
