/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.user.service;

import com.example.user.entity.Role;
import com.example.user.repository.RoleRepository;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import com.example.user.exception.AlreadyExistsException;
import com.example.user.model.SignupRequest;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author ianur
 */
@Slf4j
@Service
public class SignupService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public User register(SignupRequest request) throws AlreadyExistsException {

        if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
            throw new AlreadyExistsException("User Already Exists");
        }

        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new AlreadyExistsException("Email Already Exists");
        }

        User u = new User();
        u.setUsername(request.getUsername().toUpperCase());
        u.setPassword(encoder.encode(request.getPassword()));
        u.setEmail(request.getEmail().toUpperCase());

        request.getRoles().forEach(r -> {
            if (Objects.nonNull(r) && !r.isEmpty()) {
                Role role = roleRepository.findByRoleNameIgnoreCase(r);

                if (role != null) {
                    u.getRoles().add(role);
                }
            }
        });

        return userRepository.save(u);
    }
}
