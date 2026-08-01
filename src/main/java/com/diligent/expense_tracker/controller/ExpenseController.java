package com.diligent.expense_tracker.controller;

import com.diligent.expense_tracker.dto.ExpenseRequest;
import com.diligent.expense_tracker.dto.GenericResponse;
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
    public ResponseEntity<GenericResponse<Expense>> addExpense(
            @Valid @RequestBody ExpenseRequest request) {

        Expense expense = expenseService.addExpense(request);

        GenericResponse<Expense> response = GenericResponse.<Expense>builder()
                .success(true)
                .message("Expense created successfully")
                .data(expense)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(
            summary = "Get Expenses",
            description = "Returns all expenses or filters by category"
    )
    @GetMapping
    public ResponseEntity<GenericResponse<List<Expense>>> getExpenses(
            @RequestParam(required = false) Category category) {

        List<Expense> expenses;

        if (category == null) {
            expenses = expenseService.getAllExpenses();
        } else {
            expenses = expenseService.getExpensesByCategory(category);
        }

        GenericResponse<List<Expense>> response =
                GenericResponse.<List<Expense>>builder()
                        .success(true)
                        .message("Expenses fetched successfully")
                        .data(expenses)
                        .build();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Calculate Total Expenses",
            description = "Returns overall total or total by category"
    )
    @GetMapping("/total")
    public ResponseEntity<GenericResponse<TotalExpenseResponse>> calculateTotalExpenses(
            @RequestParam(required = false) Category category) {

        TotalExpenseResponse total;

        if(category == null){
            total = expenseService.calculateTotalExpenses();
        }else{
            total = expenseService.calculateTotalExpensesByCategory(category);
        }

        GenericResponse<TotalExpenseResponse> response =
                GenericResponse.<TotalExpenseResponse>builder()
                        .success(true)
                        .message("Total calculated successfully")
                        .data(total)
                        .build();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete Expense",
            description = "Deletes an expense by its ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteExpense(@PathVariable UUID id) {

        expenseService.deleteExpense(id);

        GenericResponse<Void> response = GenericResponse.<Void>builder()
                .success(true)
                .message("Expense deleted successfully")
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }

}