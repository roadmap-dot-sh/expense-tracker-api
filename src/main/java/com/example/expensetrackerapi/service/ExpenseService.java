/*
 * ExpenseService.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.expensetrackerapi.service;

import com.example.expensetrackerapi.dto.ExpenseRequest;
import com.example.expensetrackerapi.dto.ExpenseResponse;
import com.example.expensetrackerapi.model.Expense;
import com.example.expensetrackerapi.model.User;
import com.example.expensetrackerapi.repository.ExpenseRepository;
import com.example.expensetrackerapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ExpenseService.java
 *
 * @author Nguyen
 */
@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public ExpenseResponse createExpense(String username, ExpenseRequest request) {
        User user = userService.findByUsername(username);

        Expense expense = new Expense();
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());
        expense.setUser(user);

        Expense savedExpense = expenseRepository.save(expense);
        return mapToResponse(savedExpense);
    }

    public List<ExpenseResponse> getUserExpenses(String username) {
        User user = userService.findByUsername(username);
        return expenseRepository.findByUserOrderByDateDesc(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ExpenseResponse> getExpensesByDateRange(String username, LocalDateTime startDate, LocalDateTime endDate) {
        User user = userService.findByUsername(username);
        return expenseRepository.findByUserAndDateBetweenOrderByDateDesc(user, startDate, endDate)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ExpenseResponse> getExpensesLastWeek(String username) {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
        User user = userService.findByUsername(username);
        return expenseRepository.findExpensesAfterDate(user, oneWeekAgo)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ExpenseResponse> getExpensesLastMonth(String username) {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        User user = userService.findByUsername(username);
        return expenseRepository.findExpensesAfterDate(user, oneMonthAgo)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ExpenseResponse> getExpensesLast3Months(String username) {
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);
        User user = userService.findByUsername(username);
        return expenseRepository.findExpensesAfterDate(user, threeMonthsAgo)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ExpenseResponse updateExpense(String username, Long expenseId, ExpenseRequest request) {
        User user = userService.findByUsername(username);
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You don't have permission to update this expense");
        }

        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());

        Expense updatedExpense = expenseRepository.save(expense);
        return mapToResponse(updatedExpense);
    }

    public void deleteExpense(String username, Long expenseId) {
        User user = userService.findByUsername(username);
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You don't have permission to delete this expense");
        }

        expenseRepository.delete(expense);
    }

    private ExpenseResponse mapToResponse(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getCategory(),
                expense.getDate()
        );
    }
}
