package com.diligent.expense_tracker.service;

import com.diligent.expense_tracker.dto.ExpenseRequest;
import com.diligent.expense_tracker.model.Category;
import com.diligent.expense_tracker.model.Expense;
import com.diligent.expense_tracker.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository repository;

    @Override
    public Expense addExpense(ExpenseRequest request) {

        Expense expense = Expense.builder()
                .id(UUID.randomUUID())
                .title(request.getTitle())
                .amount(request.getAmount())
                .category(request.getCategory())
                .date(request.getDate())
                .build();

        return repository.save(expense);
    }

    @Override
    public List<Expense> getAllExpenses() {
        return repository.findAll();
    }

    @Override
    public List<Expense> getExpensesByCategory(Category category) {
        return repository.findByCategory(category);
    }

}