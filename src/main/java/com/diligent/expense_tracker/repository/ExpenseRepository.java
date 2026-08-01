package com.diligent.expense_tracker.repository;

import com.diligent.expense_tracker.model.Expense;

import java.util.List;

public interface ExpenseRepository {

    Expense save(Expense expense);

    List<Expense> findAll();

}