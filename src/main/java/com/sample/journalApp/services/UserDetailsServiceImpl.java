package com.sample.journalApp.services;

import com.sample.journalApp.models.AppUser;
import com.sample.journalApp.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Reached to UserDetailsService");
        Optional<AppUser> user = appUserRepository.findByUsername(username);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        String[] rolesArray = user.get().getRoles().toArray(new String[0]);
        return User.withUsername(user.get().getUsername())
                .password(user.get().getPassword())
                .roles(rolesArray)
                .build();
    }

}
