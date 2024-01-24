package com.blogapp.service.impl;

import com.blogapp.dao.CategoryDao;
import com.blogapp.entities.Category;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto getCategoryById(int categoryId) {
        Category category = categoryDao.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        return categoryToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categoryList = categoryDao.findAll();
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for(Category category: categoryList) {
            categoryDtoList.add(categoryToDto(category));
        }
        return categoryDtoList;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryDao.save(dtoToCategory(categoryDto));
        return categoryToDto(category);
    }

    @Override
    public CategoryDto updateCategory(int categoryId, CategoryDto categoryDto) {
        Category category = categoryDao.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Category updatedCategory = categoryDao.save(dtoToCategory(categoryDto));
        return categoryToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(int categoryId) {
        Category category = categoryDao.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryDao.deleteById(categoryId);
    }

    public Category dtoToCategory(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }

    public CategoryDto categoryToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }
}
