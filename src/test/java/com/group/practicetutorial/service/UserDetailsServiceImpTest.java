package com.group.practicetutorial.service;


import com.group.practicetutorial.entity.Role;
import com.group.practicetutorial.entity.User;
import com.group.practicetutorial.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


public class UserDetailsServiceImpTest {

    @InjectMocks
    private CustomUserDetailsImp customUserDetailsImp;

    @Mock
    private UsersRepository usersRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUserNameTest(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");

        Role role = new Role();
        role.setName("USER");

        user.getRoles().add(role);

        when(usersRepository.findUserByUsername(ArgumentMatchers.anyString())).thenReturn(user);
        UserDetails userDetails= customUserDetailsImp.loadUserByUsername("test");
        assertNotNull(userDetails);
    }
}

