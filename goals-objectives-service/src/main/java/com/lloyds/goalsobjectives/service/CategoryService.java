package com.lloyds.goalsobjectives.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lloyds.goalsobjectives.domain.Category;
import com.lloyds.goalsobjectives.repository.CategoryRepository;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public List<Category> listAllCategories() {
		return categoryRepository.findAll();
	}
}