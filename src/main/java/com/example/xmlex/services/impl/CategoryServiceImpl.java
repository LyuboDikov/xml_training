package com.example.xmlex.services.impl;

import com.example.xmlex.models.dtos.CategorySeedDto;
import com.example.xmlex.models.entities.Category;
import com.example.xmlex.repositories.CategoryRepository;
import com.example.xmlex.services.CategoryService;
import com.example.xmlex.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCategories(List<CategorySeedDto> categories) {
        categories.stream()
                .map(categorySeedDto -> modelMapper.map(categorySeedDto, Category.class))
                .forEach(categoryRepository::save);
    }
}
