package com.diligent.expense_tracker.controller;

import com.diligent.expense_tracker.dto.ExpenseRequest;
import com.diligent.expense_tracker.dto.TotalExpenseResponse;
import com.diligent.expense_tracker.model.Category;
import com.diligent.expense_tracker.model.Expense;
import com.diligent.expense_tracker.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Expense> addExpense(
            @Valid @RequestBody ExpenseRequest request) {

        Expense expense = expenseService.addExpense(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(expense);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(
            @RequestParam(required = false) Category category) {

        if (category == null) {
            return ResponseEntity.ok(expenseService.getAllExpenses());
        }

        return ResponseEntity.ok(expenseService.getExpensesByCategory(category));
    }

    @GetMapping("/total")
    public ResponseEntity<TotalExpenseResponse> calculateTotalExpenses() {

        return ResponseEntity.ok(expenseService.calculateTotalExpenses());

    }

}