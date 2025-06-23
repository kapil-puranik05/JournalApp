package com.sample.journalApp.controllers;

import com.sample.journalApp.models.AppUser;
import com.sample.journalApp.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<AppUser> users = appUserService.getAllUsers();
        if(users != null) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
