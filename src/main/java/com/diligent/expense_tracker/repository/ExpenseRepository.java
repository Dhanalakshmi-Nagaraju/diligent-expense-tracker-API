package com.diligent.expense_tracker.repository;

import com.diligent.expense_tracker.model.Expense;

public interface ExpenseRepository {

    Expense save(Expense expense);

}