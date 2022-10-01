/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.user.configuration;

import com.example.user.entity.Role;
import com.example.user.repository.RoleRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 *
 * @author ianur
 */
@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        createRoleIfNotFound("USER");
        createRoleIfNotFound("ADMIN");

        alreadySetup = true;
    }

    @Transactional
    public void createRoleIfNotFound(String name) {

        Role role = roleRepository.findByRoleNameIgnoreCase(name);

        if (role == null) {
            role = new Role();
            role.setRoleName(name);
            roleRepository.save(role);
        }
    }
}
