package com.diligent.expense_tracker.controller;

import com.diligent.expense_tracker.dto.ExpenseRequest;
import com.diligent.expense_tracker.dto.TotalExpenseResponse;
import com.diligent.expense_tracker.model.Category;
import com.diligent.expense_tracker.model.Expense;
import com.diligent.expense_tracker.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Expense API",
        description = "Operations related to Expense Management"
)
@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @Operation(
            summary = "Add Expense",
            description = "Creates a new expense"
    )
    @PostMapping
    public ResponseEntity<Expense> addExpense(
            @Valid @RequestBody ExpenseRequest request) {

        Expense expense = expenseService.addExpense(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(expense);
    }

    @Operation(
            summary = "Get Expenses",
            description = "Returns all expenses or filters by category"
    )
    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(
            @RequestParam(required = false) Category category) {

        if (category == null) {
            return ResponseEntity.ok(expenseService.getAllExpenses());
        }

        return ResponseEntity.ok(expenseService.getExpensesByCategory(category));
    }

    @Operation(
            summary = "Calculate Total Expenses",
            description = "Returns overall total or total by category"
    )
    @GetMapping("/total")
    public ResponseEntity<TotalExpenseResponse> calculateTotalExpenses(
            @RequestParam(required = false) Category category) {

        if (category == null) {
            return ResponseEntity.ok(expenseService.calculateTotalExpenses());
        }

        return ResponseEntity.ok(
                expenseService.calculateTotalExpensesByCategory(category));
    }

    @Operation(
            summary = "Delete Expense",
            description = "Deletes an expense by its ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable UUID id) {

        expenseService.deleteExpense(id);

        return ResponseEntity.noContent().build();
    }

}