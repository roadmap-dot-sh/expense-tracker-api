/*
 * AuthResponse.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.expensetrackerapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AuthResponse.java
 *
 * @author Nguyen
 */
@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String username;
    private String email;
}
