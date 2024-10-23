package com.group.practicetutorial.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group.practicetutorial.entity.Role;
import com.group.practicetutorial.entity.User;
import com.group.practicetutorial.service.RoleService;
import com.group.practicetutorial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;


    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("")
    public User createUser(@RequestBody User user) {
        User checkUserInDB = userService.findUserByUsername(user.getUsername());
        if (checkUserInDB != null) {
            throw new RuntimeException("Username already exists: " + user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleService.findByName("USER");
        if (userRole == null) {
            throw new RuntimeException("User role not found");
        }
        user.getRoles().add(userRole);
        return userService.saveUser(user);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody User updatedUser) {
        User userInDB = userService.findUserByUsername(username);

        if (userInDB != null) {
            userInDB.setUsername(updatedUser.getUsername());
            userInDB.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            userService.saveUser(userInDB);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<?> deleteUser(@PathVariable String userName) {
        User user = userService.findUserByUsername(userName);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            role.getUsers().clear();
        }

        boolean removed = userService.deleteUserById(user.getId());
        if (removed) return new ResponseEntity<>("user deleted", HttpStatus.NO_CONTENT);
        return new ResponseEntity<>("Could not be removed",HttpStatus.NOT_FOUND);
    }

}
