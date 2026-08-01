package com.diligent.expense_tracker.repository;

import com.diligent.expense_tracker.model.Expense;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryExpenseRepository implements ExpenseRepository {

    private final ConcurrentHashMap<UUID, Expense> expenses = new ConcurrentHashMap<>();

    @Override
    public Expense save(Expense expense) {

        expenses.put(expense.getId(), expense);

        return expense;
    }

}