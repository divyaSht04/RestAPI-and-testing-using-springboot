package com.group.practicetutorial.service;

import com.group.practicetutorial.entity.Journal;
import com.group.practicetutorial.entity.User;
import com.group.practicetutorial.repository.JournalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalService {

    @Autowired
    public JournalRepository journalRepo;

    @Autowired
    public UserService userService;

    public List<Journal> getAllJournals() {
        return journalRepo.findAll();
    }

    @Transactional
    public void saveJournal(Journal journal, String username) {
        User user = userService.findUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found with username: " + username);
        }
        Journal savedJournal = journalRepo.save(journal);
        user.getJournals().add(savedJournal);
        userService.saveUser(user);
    }

    public Optional<Journal> findById(Long id) {
        return journalRepo.findById(id);
    }

    public void updateJournal(Journal journal, long id) {
        Optional<Journal> journalInDB = journalRepo.findById(id);
        if (journalInDB.isPresent()) {
            Journal updateJournal = journalInDB.get();
            updateJournal.setHeading(journal.getHeading());
            updateJournal.setContent(journal.getContent());
            journalRepo.save(updateJournal);
        }
    }

    public boolean deleteById(Long id, String userName) {
        try {
            User user = userService.findUserByUsername(userName);
            boolean removed = user.getJournals().removeIf(x -> x.getId() == id);

            if (!removed) {
                return false;
            }
            userService.saveUser(user);
            journalRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete journal with id " + id);
        }
    }

}

