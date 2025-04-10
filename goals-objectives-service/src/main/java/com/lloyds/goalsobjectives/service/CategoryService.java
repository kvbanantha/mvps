package com.lloyds.goalsobjectives.service;

import com.lloyds.goalsobjectives.domain.Category;
import java.util.List;

//CategoryService interface.
public interface CategoryService {
    //method to create new category
    Category createCategory(Category category);
    //method to update existing category
    Category updateCategory(Category category);
    //method to get all the list of categories
    List<Category> listCategories();
    //method to get category by id
    Category getCategory(Long id);
}