/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.user.controller;

import com.example.user.security.jwt.JwtUtil;
import com.example.user.model.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ianur
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 4800)
@RequestMapping("/api/v1/user")
public class TestController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping(value = "/auth/validate", produces = "application/json")
    public ResponseEntity<Object> validate(@RequestHeader(name = "Authorization") String token) {
        log.info("inside validate controller\n");

        try {

            if (token.contains("Bearer ")) {
                token = token.substring(7);
            }

            jwtUtil.tokenTest(token);

            if (jwtUtil.validateToken(token)) {
                return ResponseEntity.ok().body(new MessageDTO("Valid"));
            } else {
                return new ResponseEntity<>(new MessageDTO("Invalid"), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("Exception during token validation due to Error={}", e.toString());
            return new ResponseEntity<>(new MessageDTO("Something went wrong"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/auth/hello", produces = "application/json")
    public MessageDTO hello(@RequestHeader(name = "Authorization") String token) {
        log.info("inside hello controller\n");

        return new MessageDTO("Hello Public there");
    }

    @GetMapping("/greeting")
    @PreAuthorize("isAuthenticated()")
    public MessageDTO userAccess() {
        return new MessageDTO("Congratulations! You are an authenticated user.");
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') ")
    public MessageDTO employeeAccess() {

        return new MessageDTO("User zone");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public MessageDTO adminAccess() {
        return new MessageDTO("Admin zone");
    }
}
