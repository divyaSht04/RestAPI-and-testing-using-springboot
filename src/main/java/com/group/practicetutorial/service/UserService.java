package com.group.practicetutorial.service;

import com.group.practicetutorial.entity.User;
import com.group.practicetutorial.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    public UsersRepository usersRepository;


    public List<User> getAllUsers(){
        return usersRepository.findAll();
    }

    public User saveUser(User user){
        return usersRepository.save(user);
    }

    public Optional<User> findById(Long id){
        return usersRepository.findById(id);
    }

    @Transactional
    public boolean deleteUserById(Long id) {
        Optional<User> optionalUser = usersRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
    
            if (user.getJournals() != null) {
                user.getJournals().clear();
            }
            usersRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Unable to delete user with id " + id);
        }
    }


    public User findUserByUsername(String username){
        return usersRepository.findUserByUsername(username);
    }

    public List<User> getAllAdmin() {
        List<User> users = getAllUsers();
    
        return users.stream()
                .filter(user -> user.getRoles().stream()
                            .anyMatch(role -> role.getName().contains("ADMIN")))
                .toList();
    }
}
