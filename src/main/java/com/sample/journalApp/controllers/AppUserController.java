package com.sample.journalApp.controllers;

import com.sample.journalApp.models.AppUser;
import com.sample.journalApp.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping
    public ResponseEntity<?> getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        AppUser user = appUserService.getUser(username);
        if(user != null && user.getJournalEntries() != null) {
            return new ResponseEntity<>(user.getJournalEntries(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<String> updatePassword(@RequestBody AppUser appUser) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if(appUserService.updateUser(username,appUser)) {
            return new ResponseEntity<>("Updated",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if(appUserService.deleteUser(username)) {
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        return new ResponseEntity<>("Error",HttpStatus.NOT_FOUND);
    }

}
