/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.user.controller;

import com.example.user.entity.User;
import com.example.user.exception.AlreadyExistsException;
import com.example.user.model.MessageDTO;
import com.example.user.model.SignupRequest;
import com.example.user.service.SignupService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class SignupController {

    @Autowired
    private SignupService signupService;

    @PostMapping(value = "/auth/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> register(@Valid @RequestBody SignupRequest request) throws AlreadyExistsException {
        log.info("inside register controller\n");
        User user = signupService.register(request);

        if (user == null) {
            return new ResponseEntity<>(new MessageDTO("User Not Registered"), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new MessageDTO("User Registered"), HttpStatus.OK);
        }
    }
}
