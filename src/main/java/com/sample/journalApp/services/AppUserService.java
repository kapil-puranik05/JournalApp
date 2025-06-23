package com.sample.journalApp.services;

import com.sample.journalApp.models.AppUser;
import com.sample.journalApp.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registerUser(AppUser user) {
        if(appUserRepository.findByUsername(user.getUsername()).isPresent()) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new ArrayList<>(Arrays.asList("USER")));
        appUserRepository.save(user);
        return true;
    }

    public boolean registerAdmin(AppUser admin) {
        if(appUserRepository.findByUsername(admin.getUsername()).isPresent()) {
            return false;
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRoles(new ArrayList<>(Arrays.asList("ADMIN")));
        appUserRepository.save(admin);
        return true;
    }

    public AppUser getUser(String username) {
        if(appUserRepository.findByUsername(username).isEmpty()) {
            return null;
        }
        return appUserRepository.findByUsername(username).get();
    }

    public boolean updateUser(String username, AppUser user) {
        Optional<AppUser> existingUserOpt = appUserRepository.findByUsername(username);
        if (existingUserOpt.isEmpty()) return false;
        AppUser existingUser = existingUserOpt.get();
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getJournalEntries() != null && !user.getJournalEntries().isEmpty()) {
            existingUser.setJournalEntries(user.getJournalEntries());
        }
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            existingUser.setRoles(user.getRoles());
        }
        else if (existingUser.getRoles() == null || existingUser.getRoles().isEmpty()) {
            existingUser.setRoles(List.of("USER"));
        }
        appUserRepository.save(existingUser);
        return true;
    }

    public boolean deleteUser(String username) {
        if(appUserRepository.findByUsername(username).isEmpty()) {
            return false;
        }
        appUserRepository.deleteByUsername(username);
        return true;
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

}
