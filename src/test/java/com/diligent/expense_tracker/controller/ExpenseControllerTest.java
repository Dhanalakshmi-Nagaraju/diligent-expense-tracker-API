package com.diligent.expense_tracker.controller;

import com.diligent.expense_tracker.dto.ExpenseRequest;
import com.diligent.expense_tracker.model.Category;
import com.diligent.expense_tracker.model.Expense;
import com.diligent.expense_tracker.service.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        when(expenseService.addExpense(any(ExpenseRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Coffee"))
                .andExpect(jsonPath("$.amount").value(120))
                .andExpect(jsonPath("$.category").value("FOOD"));
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
                .andExpect(status().isBadRequest());
    }
}