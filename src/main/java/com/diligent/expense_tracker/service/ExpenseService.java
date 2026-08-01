package com.diligent.expense_tracker.service;

import com.diligent.expense_tracker.dto.ExpenseRequest;
import com.diligent.expense_tracker.model.Expense;

public interface ExpenseService {

    Expense addExpense(ExpenseRequest request);

}