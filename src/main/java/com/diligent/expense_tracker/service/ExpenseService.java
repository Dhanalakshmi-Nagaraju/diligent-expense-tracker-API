package com.diligent.expense_tracker.service;

import com.diligent.expense_tracker.dto.ExpenseRequest;
import com.diligent.expense_tracker.dto.TotalExpenseResponse;
import com.diligent.expense_tracker.model.Category;
import com.diligent.expense_tracker.model.Expense;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {

    Expense addExpense(ExpenseRequest request);

    List<Expense> getAllExpenses();

    List<Expense> getExpensesByCategory(Category category);

    TotalExpenseResponse calculateTotalExpenses();

    TotalExpenseResponse calculateTotalExpensesByCategory(Category category);

    void deleteExpense(UUID id);

}