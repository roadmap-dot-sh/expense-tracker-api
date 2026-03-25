/*
 * ExpenseController.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.expensetrackerapi.controller;

import com.example.expensetrackerapi.dto.ExpenseRequest;
import com.example.expensetrackerapi.dto.ExpenseResponse;
import com.example.expensetrackerapi.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ExpenseController.java
 *
 * @author Nguyen
 */
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(@Valid @RequestBody ExpenseRequest request) {
        String username = getCurrentUsername();
        ExpenseResponse expense = expenseService.createExpense(username, request);
        return ResponseEntity.ok(expense);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses() {
        String username = getCurrentUsername();
        List<ExpenseResponse> expenses = expenseService.getUserExpenses(username);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/filter/week")
    public ResponseEntity<List<ExpenseResponse>> getExpensesLastWeek() {
        String username = getCurrentUsername();
        List<ExpenseResponse> expenses = expenseService.getExpensesLastWeek(username);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/filter/month")
    public ResponseEntity<List<ExpenseResponse>> getExpensesLastMonth() {
        String username = getCurrentUsername();
        List<ExpenseResponse> expenses = expenseService.getExpensesLastMonth(username);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/filter/3months")
    public ResponseEntity<List<ExpenseResponse>> getExpensesLast3Months() {
        String username = getCurrentUsername();
        List<ExpenseResponse> expenses = expenseService.getExpensesLast3Months(username);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/filter/custom")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        String username = getCurrentUsername();
        List<ExpenseResponse> expenses = expenseService.getExpensesByDateRange(username, startDate, endDate);
        return ResponseEntity.ok(expenses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequest request) {
        String username = getCurrentUsername();
        ExpenseResponse expense = expenseService.updateExpense(username, id, request);
        return ResponseEntity.ok(expense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        String username = getCurrentUsername();
        expenseService.deleteExpense(username, id);
        return ResponseEntity.noContent().build();
    }
}
