/*
 * ExpenseResponse.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.expensetrackerapi.dto;

import com.example.expensetrackerapi.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ExpenseResponse.java
 *
 * @author Nguyen
 */
@Data
@AllArgsConstructor
public class ExpenseResponse {
    private Long id;
    private String description;
    private Double amount;
    private Category category;
    private LocalDateTime date;
}
