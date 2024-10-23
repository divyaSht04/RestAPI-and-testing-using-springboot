package com.group.practicetutorial.controller;

import com.group.practicetutorial.entity.Role;
import com.group.practicetutorial.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RoleControllerTest {

    @InjectMocks
    private RoleController roleController;

    @Mock
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getRoles() {

        Role role = new Role();
        role.setName("Role test 1");
        role.setId(311211L);

        List<Role> roles = new ArrayList<>();
        roles.add(role);


        when(roleService.getAllRoles()).thenReturn(roles);
        assertNotNull(roleController.getRoles());
    }

    @Test
    void createRole() {
        Role role = new Role();
        role.setName("Role test 2");

        when(roleService.findByName(ArgumentMatchers.anyString())).thenReturn(null);
        when(roleService.saveRole(any(Role.class))).thenReturn(role);

        ResponseEntity<?> response = roleController.createRole(role);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}