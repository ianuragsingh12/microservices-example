/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.user.exception;

import javax.naming.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author ianur
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AlreadyExistsException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public AlreadyExistsException(String msg) {
        super(msg);
    }
}
