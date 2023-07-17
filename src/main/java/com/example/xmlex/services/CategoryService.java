package com.example.xmlex.services;

import com.example.xmlex.models.dtos.CategorySeedDto;

import java.util.List;

public interface CategoryService {
    void seedCategories(List<CategorySeedDto> categories);
}
