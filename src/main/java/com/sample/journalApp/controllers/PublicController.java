package com.sample.journalApp.controllers;

import com.sample.journalApp.models.AppUser;
import com.sample.journalApp.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "0kay";
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody AppUser appUser) {
        if(appUserService.registerUser(appUser)) {
            return new ResponseEntity<>("Added", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
