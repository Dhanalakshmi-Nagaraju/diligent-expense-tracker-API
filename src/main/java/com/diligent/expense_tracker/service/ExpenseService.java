package com.diligent.expense_tracker.service;

import com.diligent.expense_tracker.dto.ExpenseRequest;
import com.diligent.expense_tracker.model.Category;
import com.diligent.expense_tracker.model.Expense;

import java.util.List;

public interface ExpenseService {

    Expense addExpense(ExpenseRequest request);

    List<Expense> getAllExpenses();

    List<Expense> getExpensesByCategory(Category category);

}