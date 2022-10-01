/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.user.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ianur
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String userId;
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;
    private List<String> roles;
}
