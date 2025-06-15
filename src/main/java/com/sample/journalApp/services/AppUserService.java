package com.sample.journalApp.services;

import com.sample.journalApp.models.AppUser;
import com.sample.journalApp.repositories.AppUserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public boolean registerUser(AppUser user) {
        if(appUserRepository.findByUsername(user.getUsername()).isPresent()) {
            return false;
        }
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        appUserRepository.save(user);
        return true;
    }

    public AppUser getUser(String username) {
        if(appUserRepository.findByUsername(username).isEmpty()) {
            return null;
        }
        return appUserRepository.findByUsername(username).get();
    }

    public boolean updateUser(String username,AppUser user) {
        if(appUserRepository.findByUsername(username).isEmpty()) {
            return false;
        }
        AppUser old = appUserRepository.findByUsername(username).get();
        old.setPassword(!user.getPassword().isEmpty() ? user.getPassword() : old.getPassword());
        old.setJournalEntries(!user.getJournalEntries().isEmpty() && user.getJournalEntries() != null ? user.getJournalEntries() : old.getJournalEntries());
        appUserRepository.save(old);
        return true;
    }

}
