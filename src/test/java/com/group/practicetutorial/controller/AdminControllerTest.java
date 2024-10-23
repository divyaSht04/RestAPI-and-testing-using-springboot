package com.group.practicetutorial.controller;

import com.group.practicetutorial.entity.Role;
import com.group.practicetutorial.entity.User;
import com.group.practicetutorial.service.RoleService;
import com.group.practicetutorial.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private UserService userService;

    @Mock
    RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllAdminUsersTest(){
        User user = new User();
        user.setUsername("test1");
        user.setPassword("test1");

        Role role = new Role();
        role.setName("USER_TEST");

        user.getRoles().add(role);
        List<User> list = new ArrayList<>();
        list.add(user);

        when(userService.getAllAdmin()).thenReturn(list);
        assertNotNull(adminController.allAdmin());
    }

    @Test
    public void createAdminTest(){
        User user = new User();
        user.setUsername("test2");
        user.setPassword("test2");

        Role role = new Role();
        role.setName("USER_TEST");

        user.getRoles().add(role);

        when(roleService.findByName("ADMIN")).thenReturn(role);
        when(userService.findUserByUsername(user.getUsername())).thenReturn(user);
        when(userService.saveUser(any(User.class))).thenReturn(user);
        assertNotNull(adminController.createAdmin(user));
    }
}
