package com.diligent.expense_tracker.controller;

import com.diligent.expense_tracker.dto.ExpenseRequest;
import com.diligent.expense_tracker.dto.GenericResponse;
import com.diligent.expense_tracker.dto.TotalExpenseResponse;
import com.diligent.expense_tracker.model.Category;
import com.diligent.expense_tracker.model.Expense;
import com.diligent.expense_tracker.service.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExpenseController.class)
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ExpenseService expenseService;

    @Test
    void shouldCreateExpenseSuccessfully() throws Exception {

        ExpenseRequest request = ExpenseRequest.builder()
                .title("Coffee")
                .amount(BigDecimal.valueOf(120))
                .category(Category.FOOD)
                .date(LocalDate.parse("2026-08-01"))
                .build();

        Expense response = Expense.builder()
                .id(UUID.randomUUID())
                .title("Coffee")
                .amount(BigDecimal.valueOf(120))
                .category(Category.FOOD)
                .date(LocalDate.parse("2026-08-01"))
                .build();

        when(expenseService.addExpense(any()))
                .thenReturn(response);

        mockMvc.perform(post("/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Expense created successfully"))
                .andExpect(jsonPath("$.data.title").value("Coffee"))
                .andExpect(jsonPath("$.data.amount").value(120))
                .andExpect(jsonPath("$.data.category").value("FOOD"));
    }

    @Test
    void shouldReturnBadRequestWhenRequestIsInvalid() throws Exception {

        ExpenseRequest request = ExpenseRequest.builder()
                .title("")
                .amount(BigDecimal.valueOf(-100))
                .build();

        mockMvc.perform(post("/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void shouldReturnAllExpenses() throws Exception {

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

        when(expenseService.getAllExpenses())
                .thenReturn(expenses);

        mockMvc.perform(get("/expenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].title").value("Coffee"))
                .andExpect(jsonPath("$.data[1].title").value("Movie"));
    }

    @Test
    void shouldReturnExpensesByCategory() throws Exception {

        List<Expense> expenses = List.of(
                Expense.builder()
                        .title("Coffee")
                        .amount(BigDecimal.valueOf(120))
                        .category(Category.FOOD)
                        .date(LocalDate.now())
                        .build()
        );

        when(expenseService.getExpensesByCategory(Category.FOOD))
                .thenReturn(expenses);

        mockMvc.perform(get("/expenses")
                        .param("category", "FOOD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].category").value("FOOD"));
    }

    @Test
    void shouldReturnTotalExpenses() throws Exception {

        TotalExpenseResponse response = TotalExpenseResponse.builder()
                .total(BigDecimal.valueOf(600))
                .build();

        when(expenseService.calculateTotalExpenses())
                .thenReturn(response);

        mockMvc.perform(get("/expenses/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.total").value(600));
    }

    @Test
    void shouldReturnTotalExpensesByCategory() throws Exception {

        TotalExpenseResponse response = TotalExpenseResponse.builder()
                .total(BigDecimal.valueOf(350))
                .build();

        when(expenseService.calculateTotalExpensesByCategory(Category.FOOD))
                .thenReturn(response);

        mockMvc.perform(get("/expenses/total")
                        .param("category", "FOOD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.total").value(350));
    }

    @Test
    void shouldDeleteExpenseSuccessfully() throws Exception {

        UUID id = UUID.randomUUID();

        doNothing().when(expenseService).deleteExpense(id);

        mockMvc.perform(delete("/expenses/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Expense deleted successfully"));
    }
}