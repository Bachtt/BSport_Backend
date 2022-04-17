package com.bachtt.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bachtt.models.Category;
import com.bachtt.repositories.CategoryRepository;
import com.bachtt.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<Category> findAllCategory() {
		return categoryRepository.findAll();
	}

	@Override
	public Optional<Category> findByCategoryId(Long categoryId) {
		return categoryRepository.findById(categoryId);
	}

	@Override
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public void removeCategory(Long categoryId) {
		categoryRepository.deleteById(categoryId);
		
	}

	@Override
	public List<Category> findByCategoryName(String categoryName) {
		return categoryRepository.findByCategoryName(categoryName);
	}

}
