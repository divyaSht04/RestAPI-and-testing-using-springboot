package com.group.practicetutorial.controller;

import com.group.practicetutorial.entity.Journal;
import com.group.practicetutorial.entity.User;
import com.group.practicetutorial.service.JournalService;
import com.group.practicetutorial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalController {

    // Controller for handling journal-related requests
    // Implement methods to handle CRUD operations for journal entries, like creating, reading, updating, and deleting.
    // Use appropriate HTTP methods (GET, POST, PUT, DELETE) to interact with the API.

    @Autowired
    public JournalService journalService;


    @Autowired
    private UserService userService;

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @GetMapping("/get-all-jourmals")
    public ResponseEntity<?> getAllJournal(){

        List<Journal> journals = journalService.getAllJournals();

        if (!journals.isEmpty()){
            return new ResponseEntity<>(journals, HttpStatus.OK);
        }

        return new ResponseEntity<>("No journal entries", HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<List<?>> getEntries() {
        // Method to get all journal entries
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findUserByUsername(userName);
        if (user != null) {
            List<Journal> alldata = user.getJournals();
            if (alldata.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(alldata, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("")
    public ResponseEntity<?> createEntry(@RequestBody Journal journalEntity) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName(); // gets the username from the Authentication!

            journalEntity.setDateRN(LocalDate.now());
            journalService.saveJournal(journalEntity, userName);
            return new ResponseEntity<>(journalEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> getJournalByID(@PathVariable Long id, @RequestBody Journal journalEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findUserByUsername(userName);
        List<Journal> journalList = user.getJournals().stream().filter(journal -> journal.getId() == id).toList();

        if (!journalList.isEmpty()) {
            Optional<?> journal = journalService.findById(id);
            if (journal.isPresent()) {
                journalService.updateJournal(journalEntity, id);
                return new ResponseEntity<>(journal.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("No Such Entry", HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> getJournalByID(@PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findUserByUsername(userName);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            if (user.getJournals() != null) {
                Optional<Journal> journalOptional = user.getJournals().stream()
                        .filter(journal -> journal.getId() == id)
                        .findFirst();

                if (journalOptional.isPresent()) {
                    Journal journal = journalOptional.get();
                    return new ResponseEntity<>(journal, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("No journal entry found with the given ID", HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>("No journal Entries", HttpStatus.NO_CONTENT);
            }
        }
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> removeJournalById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = journalService.deleteById(id, userName);
        if (removed) {
            return new ResponseEntity<>("Removed", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("No Entry found", HttpStatus.NO_CONTENT);
        }
    }

}

