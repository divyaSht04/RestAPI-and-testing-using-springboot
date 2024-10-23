package com.group.practicetutorial.service;

import com.group.practicetutorial.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetAllUsers(){
        List<User> users = userService.getAllUsers();
        assertEquals(1, users.size());
    }

    @Test
    public void testFindByUserName(){
        assertNotNull(userService.findUserByUsername("icely"));
    }

    @ParameterizedTest
    @CsvSource({
            "icely",
            "divya",
            "beast"
    })
    public void testFindJournalEntriesOfUsers(String name){
        User users =  userService.findUserByUsername(name);
        assertTrue(users.getJournals().isEmpty());
    }



    @ParameterizedTest
    @ValueSource(strings = {
            "icely",
            "divya",
            "beast"
    })
    public void testFindJournalEntriesByUsername(String name){
        User users =  userService.findUserByUsername(name);
        assertTrue(users.getJournals().isEmpty());
    }



}
