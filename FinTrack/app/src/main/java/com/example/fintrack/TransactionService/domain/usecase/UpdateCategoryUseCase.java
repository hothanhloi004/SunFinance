package com.example.fintrack.TransactionService.domain.usecase;

import com.example.fintrack.TransactionService.data.dao.CategoryDao;
import com.example.fintrack.TransactionService.data.entity.CategoryEntity;

public class UpdateCategoryUseCase {

    private final CategoryDao categoryDao;

    public UpdateCategoryUseCase(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public void execute(CategoryEntity category) {
        categoryDao.update(category);
    }
}
