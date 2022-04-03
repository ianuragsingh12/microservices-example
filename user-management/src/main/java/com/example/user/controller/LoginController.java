/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.user.controller;

import com.example.user.exception.InvalidLoginException;
import com.example.user.configuration.JwtUserDetailsService;
import com.example.user.configuration.JwtUtil;
import com.example.user.entity.Role;
import com.example.user.entity.User;
import com.example.user.model.LoginRequest;
import com.example.user.model.LoginResponse;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ianur
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @PostMapping(value = "/auth/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest request) throws InvalidLoginException {
        log.info("inside login controller\n");
        authenticate(request.getUsername(), request.getPassword());

        User user = jwtUserDetailsService.getUserByUsername(request.getUsername());

        UserDetails userdetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), new ArrayList<>());

        final String token = jwtUtil.generateToken(userdetails);

        LoginResponse rs = new LoginResponse();
        rs.setUserId(user.getUserId());
        rs.setToken(token);
        rs.setRoles(user.getRoles()
                .stream()
                .map(Role::getRoleName)
                .collect(Collectors.toSet()));

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws InvalidLoginException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new InvalidLoginException("USER_DISABLED");
        } catch (BadCredentialsException e) {
            throw new InvalidLoginException("Incorrect Username/Password");
        }
    }
}
