package com.diligent.expense_tracker.service;

import com.diligent.expense_tracker.dto.ExpenseRequest;
import com.diligent.expense_tracker.dto.TotalExpenseResponse;
import com.diligent.expense_tracker.exception.ExpenseNotFoundException;
import com.diligent.expense_tracker.model.Category;
import com.diligent.expense_tracker.model.Expense;
import com.diligent.expense_tracker.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository repository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @Test
    void shouldAddExpenseSuccessfully() {

        ExpenseRequest request = ExpenseRequest.builder()
                .title("Coffee")
                .amount(BigDecimal.valueOf(120))
                .category(Category.FOOD)
                .date(LocalDate.now())
                .build();

        when(repository.save(any(Expense.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Expense savedExpense = expenseService.addExpense(request);

        assertNotNull(savedExpense);
        assertNotNull(savedExpense.getId());
        assertEquals("Coffee", savedExpense.getTitle());
        assertEquals(BigDecimal.valueOf(120), savedExpense.getAmount());
        assertEquals(Category.FOOD, savedExpense.getCategory());

        verify(repository).save(any(Expense.class));
    }

    @Test
    void shouldGenerateUuidBeforeSaving() {

        ExpenseRequest request = ExpenseRequest.builder()
                .title("Lunch")
                .amount(BigDecimal.valueOf(250))
                .category(Category.FOOD)
                .date(LocalDate.now())
                .build();

        when(repository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        expenseService.addExpense(request);

        ArgumentCaptor<Expense> captor = ArgumentCaptor.forClass(Expense.class);

        verify(repository).save(captor.capture());

        Expense expense = captor.getValue();

        assertNotNull(expense.getId());
    }

    @Test
    void shouldReturnAllExpenses() {

        List<Expense> expenses = List.of(
                Expense.builder()
                        .id(UUID.randomUUID())
                        .title("Coffee")
                        .amount(BigDecimal.valueOf(120))
                        .category(Category.FOOD)
                        .date(LocalDate.now())
                        .build(),

                Expense.builder()
                        .id(UUID.randomUUID())
                        .title("Movie")
                        .amount(BigDecimal.valueOf(300))
                        .category(Category.ENTERTAINMENT)
                        .date(LocalDate.now())
                        .build()
        );

        when(repository.findAll()).thenReturn(expenses);

        List<Expense> result = expenseService.getAllExpenses();

        assertEquals(2, result.size());

        verify(repository).findAll();
    }

    @Test
    void shouldReturnExpensesByCategory() {

        List<Expense> expenses = List.of(
                Expense.builder()
                        .title("Coffee")
                        .amount(BigDecimal.valueOf(120))
                        .category(Category.FOOD)
                        .date(LocalDate.now())
                        .build()
        );

        when(repository.findByCategory(Category.FOOD))
                .thenReturn(expenses);

        List<Expense> result = expenseService.getExpensesByCategory(Category.FOOD);

        assertEquals(1, result.size());
        assertEquals(Category.FOOD, result.get(0).getCategory());

        verify(repository).findByCategory(Category.FOOD);
    }

    @Test
    void shouldCalculateTotalExpenses() {

        List<Expense> expenses = List.of(

                Expense.builder()
                        .amount(BigDecimal.valueOf(100))
                        .build(),

                Expense.builder()
                        .amount(BigDecimal.valueOf(200))
                        .build(),

                Expense.builder()
                        .amount(BigDecimal.valueOf(300))
                        .build()

        );

        when(repository.findAll()).thenReturn(expenses);

        TotalExpenseResponse response =
                expenseService.calculateTotalExpenses();

        assertEquals(BigDecimal.valueOf(600), response.getTotal());

        verify(repository).findAll();
    }

    @Test
    void shouldCalculateTotalExpensesByCategory() {

        List<Expense> expenses = List.of(

                Expense.builder()
                        .amount(BigDecimal.valueOf(100))
                        .category(Category.FOOD)
                        .build(),

                Expense.builder()
                        .amount(BigDecimal.valueOf(250))
                        .category(Category.FOOD)
                        .build()

        );

        when(repository.findByCategory(Category.FOOD))
                .thenReturn(expenses);

        TotalExpenseResponse response =
                expenseService.calculateTotalExpensesByCategory(Category.FOOD);

        assertEquals(BigDecimal.valueOf(350), response.getTotal());

        verify(repository).findByCategory(Category.FOOD);
    }

    @Test
    void shouldDeleteExpenseSuccessfully() {

        UUID id = UUID.randomUUID();

        Expense expense = Expense.builder()
                .id(id)
                .build();

        when(repository.findById(id))
                .thenReturn(Optional.of(expense));

        expenseService.deleteExpense(id);

        verify(repository).deleteById(id);
    }

    @Test
    void shouldThrowExceptionWhenExpenseNotFound() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(ExpenseNotFoundException.class,
                () -> expenseService.deleteExpense(id));

        verify(repository, never()).deleteById(any());
    }
}