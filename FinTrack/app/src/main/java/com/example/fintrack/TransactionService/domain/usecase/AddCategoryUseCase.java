package com.example.fintrack.TransactionService.domain.usecase;

import com.example.fintrack.TransactionService.data.dao.CategoryDao;
import com.example.fintrack.TransactionService.data.entity.CategoryEntity;

import java.util.UUID;

public class AddCategoryUseCase {

    private final CategoryDao categoryDao;

    public AddCategoryUseCase(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public void execute(
            String name,
            String txTypeId,
            String icon,
            String parentCategoryId,
            String userId
    ) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Tên danh mục không hợp lệ");
        }

        CategoryEntity c = new CategoryEntity();
        c.category_id = UUID.randomUUID().toString();
        c.name = name;
        c.tx_type_id = txTypeId;
        c.icon = icon;
        c.parent_category_id = parentCategoryId;
        c.user_id = userId;

        categoryDao.insert(c);
    }
}
