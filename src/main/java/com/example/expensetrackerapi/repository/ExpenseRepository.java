/*
 * ExpenseRepository.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.expensetrackerapi.repository;

import com.example.expensetrackerapi.enums.Category;
import com.example.expensetrackerapi.model.Expense;
import com.example.expensetrackerapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ExpenseRepository.java
 *
 * @author Nguyen
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserOrderByDateDesc(User user);

    List<Expense> findByUserAndDateBetweenOrderByDateDesc(User user, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT e FROM Expense e WHERE e.user = :user AND e.date >= :startDate ORDER BY e.date DESC")
    List<Expense> findExpensesAfterDate(@Param("user") User user, @Param("startDate") LocalDateTime startDate);

    List<Expense> findByUserAndCategoryOrderByDateDesc(User user, Category category);
}
