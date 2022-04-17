package com.bachtt.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bachtt.models.Category;
import com.bachtt.models.message.ResponseMessage;
import com.bachtt.services.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	// get all category
	@GetMapping("/categories")
	ResponseEntity<ResponseMessage> getAllCategories() {
		List<Category> categoryList = categoryService.findAllCategory();
		if (categoryList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseMessage("Falled", "Cannot find any categories", ""));
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseMessage("OK", "Display list categories successfully", categoryList));
	}

	// get detail category
	@GetMapping("/categories/{categoryId}")
	ResponseEntity<ResponseMessage> findByCategoryId(@PathVariable Long categoryId) {
		Optional<Category> foundCategory = categoryService.findByCategoryId(categoryId);
		return foundCategory.isPresent()
				? ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseMessage("OK", "Query category successfully", foundCategory))
				: ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ResponseMessage("Falled", "Cannot find category by id: " + categoryId, ""));
	}

	// insert new Category
	@PostMapping("/categories/insert")
	ResponseEntity<ResponseMessage> insertCategory(@RequestBody Category newCategory) {
		// category must not have the same name
		List<Category> foundCategory = categoryService.findByCategoryName(newCategory.getCategoryName().trim());
		if (foundCategory.size() > 0) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
					.body(new ResponseMessage("Falled", "Category name already taken", ""));
		}
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseMessage("OK", "insert category successfully", categoryService.saveCategory(newCategory)));
	}

	// update category
	@PutMapping("/categories/{categoryId}")
	ResponseEntity<ResponseMessage> updateCategory(@RequestBody Category newCategory, @PathVariable Long categoryId) {
		Category foundCategory = categoryService.findByCategoryId(categoryId)
//		if (!foundCategory.isPresent()) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new ResponseMessage("Falled", "Cannot find category with id: " + categoryId, ""));
//		}
		.map(category -> {
			category.setCategoryName(newCategory.getCategoryName());
			category.setDescription(newCategory.getDescription());
			return categoryService.saveCategory(category);
//		});
		}).orElseGet(() -> {
			newCategory.setCategoryId(categoryId);
			return categoryService.saveCategory(newCategory);
		});
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseMessage("OK", "Update product successfully", foundCategory));
	}

	// delete category
	@DeleteMapping("/categories/{categoryId}")
	ResponseEntity<ResponseMessage> deleteCategory(@PathVariable Long categoryId) {
		Optional<Category> foundCategory = categoryService.findByCategoryId(categoryId);
		if(!foundCategory.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Falled","Cannot find category with id: " + categoryId, ""));
		}
		categoryService.removeCategory(categoryId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("OK", "Delete category successfully", ""));
	}
}
