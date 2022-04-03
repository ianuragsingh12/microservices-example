/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.user.controller;

import com.example.user.configuration.JwtUserDetailsService;
import com.example.user.configuration.JwtUtil;
import com.example.user.model.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
@CrossOrigin
@RequestMapping("/api/user")
public class TestController {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping(value = "/auth/validate", produces = "application/json")
    public ResponseEntity<Object> validate(@RequestHeader(name = "Authorization") String token) {
        log.info("inside validate controller\n");

        try {
            token = token.substring(7);
            String username = jwtUtil.getUsernameFromToken(token);
            jwtUserDetailsService.getUserByUsername(username);

            return new ResponseEntity<>(new MessageDTO("Valid"), HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new MessageDTO("Not Valid"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO("Not Valid"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/auth/hello", produces = "application/json")
    public MessageDTO hello(@RequestHeader(name = "Authorization") String token) {
        log.info("inside hello controller\n");

        return new MessageDTO("Hello there");
    }
}
