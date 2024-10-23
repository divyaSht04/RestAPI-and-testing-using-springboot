package com.group.practicetutorial.controller;

import com.group.practicetutorial.entity.Journal;
import com.group.practicetutorial.entity.User;
import com.group.practicetutorial.service.JournalService;
import com.group.practicetutorial.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JournalControllerTest {

    @InjectMocks
    private JournalController journalController;

    @Mock
    private JournalService journalService;

    @Mock
    private UserService userService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllJournal() {
        Journal journal = new Journal();
        journal.setId(1L);
        journal.setHeading("Test Journal");
        journal.setContent("Test Journal");
        journal.setDateRN(LocalDate.now());

        List<Journal> journals = new ArrayList<>();
        journals.add(journal);

//        when(journalService.getAllJournals()).thenReturn(null);
        when(journalService.getAllJournals()).thenReturn(journals);
        ResponseEntity<?> response = journalController.getAllJournal();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(journals, response.getBody());
    }

    @Test
    void getEntries() {
        User user = new User();
        user.setId(1L);
        user.setPassword("pass");
        user.setUsername("testUser");

        Journal journal = new Journal();
        journal.setId(1L);
        journal.setHeading("Test Journal");
        journal.setContent("Test Journal");
        journal.setDateRN(LocalDate.now());
        user.getJournals().add(journal);

        List<Journal> journals = new ArrayList<>();
        journals.add(journal);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");

        when(userService.findUserByUsername(ArgumentMatchers.anyString())).thenReturn(user);

        ResponseEntity<List<?>> response = journalController.getEntries();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(journals, response.getBody());

    }

    @Test
    void createEntry() {
    }

    @Test
    void getJournalByID() {
    }

    @Test
    void testGetJournalByID() {
    }

    @Test
    void removeJournalById() {
    }
}