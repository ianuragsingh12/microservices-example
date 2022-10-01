/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gateway.configuration;

import javax.naming.AuthenticationException;

/**
 *
 * @author ianur
 */
public class JwtTokenMissingException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public JwtTokenMissingException(String msg) {
        super(msg);
    }
}
