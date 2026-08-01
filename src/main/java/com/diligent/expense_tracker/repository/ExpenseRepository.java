package com.diligent.expense_tracker.repository;

import com.diligent.expense_tracker.model.Category;
import com.diligent.expense_tracker.model.Expense;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository {

    Expense save(Expense expense);

    List<Expense> findAll();

    List<Expense> findByCategory(Category category);

    Optional<Expense> findById(UUID id);

    void deleteById(UUID id);

}