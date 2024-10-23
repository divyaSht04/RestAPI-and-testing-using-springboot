package com.group.practicetutorial.controller;

import com.group.practicetutorial.entity.Role;
import com.group.practicetutorial.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<Role> getRoles() {
        return roleService.getAllRoles();
    }

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody Role role){

        if(roleService.findByName(role.getName())!=null){
            return new ResponseEntity<>("Role already exists", HttpStatus.CONFLICT);
        }else{
            roleService.saveRole(role);
            return new ResponseEntity<>("Role created successfully", HttpStatus.CREATED);
        }

    }
}
