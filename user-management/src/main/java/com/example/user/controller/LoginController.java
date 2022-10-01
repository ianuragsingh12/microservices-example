/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.user.controller;

import com.example.user.exception.InvalidLoginException;
import com.example.user.security.jwt.JwtUtil;
import com.example.user.model.LoginRequest;
import com.example.user.model.LoginResponse;
import com.example.user.security.service.UserDetailsImpl;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/user")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value = "/auth/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest request) throws InvalidLoginException {
        log.info("inside login controller\n");

        Authentication authentication = authenticate(request.getUsername(), request.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        LoginResponse rs = new LoginResponse();
        rs.setUserId(userDetails.getUserId());
        rs.setEmail(userDetails.getEmail());
        rs.setUsername(userDetails.getUsername());
        rs.setToken(jwt);
        rs.setRoles(userDetails.getAuthorities()
                .stream()
                .map(e -> e.getAuthority())
                .collect(Collectors.toList()));

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) throws InvalidLoginException {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new InvalidLoginException("USER_DISABLED");
        } catch (BadCredentialsException e) {
            throw new InvalidLoginException("Incorrect Username/Password");
        }
    }
}
