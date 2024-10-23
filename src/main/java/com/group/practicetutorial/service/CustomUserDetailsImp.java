package com.group.practicetutorial.service;

import com.group.practicetutorial.config.CustomDetails;
import com.group.practicetutorial.entity.User;
import com.group.practicetutorial.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsImp implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = usersRepository.findUserByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException(username + " not found!");
        }

        return new CustomDetails(user);
    }
}
