package com.diligent.expense_tracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    private UUID id;

    private String title;

    private BigDecimal amount;

    private Category category;

    private LocalDate date;

}