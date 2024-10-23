package com.group.practicetutorial.controller;

import com.group.practicetutorial.entity.Role;
import com.group.practicetutorial.entity.User;
import com.group.practicetutorial.service.RoleService;
import com.group.practicetutorial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> allAdmin() {
        List<User> users = userService.getAllAdmin();
        if (!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }else return new ResponseEntity<>("No admins in database", HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<?> createAdmin(@RequestBody User user) {

        if(userService.findUserByUsername(user.getUsername()) == null) {

            Role adminRole = roleService.findByName("ADMIN");
            if (adminRole == null) {
                throw new RuntimeException("Admin role not found");
            }

            user.getRoles().add(adminRole);

            userService.saveUser(user);
            return new ResponseEntity<>("Admin created successfully", HttpStatus.CREATED);
        } else return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
    }
}
