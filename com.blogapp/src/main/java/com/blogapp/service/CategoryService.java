package com.blogapp.service;


import com.blogapp.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    public CategoryDto getCategoryById(int categoryId);
    public List<CategoryDto> getAllCategories();
    public CategoryDto createCategory(CategoryDto categoryDto);
    public CategoryDto updateCategory(int categoryId, CategoryDto categoryDto);
    public void deleteCategory(int categoryId);
}
