package com.group.practicetutorial.controller;

import com.group.practicetutorial.entity.Role;
import com.group.practicetutorial.entity.User;
import com.group.practicetutorial.service.RoleService;
import com.group.practicetutorial.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllUsers() {
        when(userService.getAllUsers()).thenReturn(
                List.of(
                        new User()
                )
        );

        assertNotNull(userService.getAllUsers());
        assertEquals(1, userService.getAllUsers().size());
    }

    @Test
    void createUser() {

        User user = new User();
        user.setUsername("test");
        user.setPassword("test");

        when(userService.findUserByUsername(ArgumentMatchers.anyString())).thenReturn(null);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");


        when(roleService.findByName("USER")).thenReturn(any(Role.class));
        when(userService.saveUser(user)).thenReturn(user);
    }

    @Test
    void getUserByUsername() {
        when(userService.findUserByUsername(ArgumentMatchers.anyString())).thenReturn(
                new User()
        );

        assertNotNull(userService.findUserByUsername("test"));
    }

    @Test
    void updateUser() {
        when(userService.findUserByUsername(ArgumentMatchers.anyString())).thenReturn(
                new User()
        );

        userController.updateUser("test", new User());
    }

    @Test
    void deleteUser() {
        when(userService.findUserByUsername(ArgumentMatchers.anyString())).thenReturn(
                new User()
        );

        userController.deleteUser("test");
    }
}