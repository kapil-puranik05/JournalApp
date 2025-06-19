package com.sample.journalApp.controllers;

import com.sample.journalApp.models.AppUser;
import com.sample.journalApp.models.JournalEntry;
import com.sample.journalApp.services.AppUserService;
import com.sample.journalApp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journalapi")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/id")
    public ResponseEntity<?> getEntry(@PathVariable String id) {
        JournalEntry journalEntry = journalEntryService.fetchEntry(new ObjectId(id));
        if(journalEntry != null) {
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<?> getUserEntries() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = appUserService.getUser(auth.getName());
        List<JournalEntry> all = journalEntryService.fetchEntries();
        if(all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{username}")
    public ResponseEntity<String> postEntry(@RequestBody JournalEntry journalEntry) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        journalEntry.setDate(LocalDateTime.now());
        if(journalEntryService.registerEntry(journalEntry, auth.getName())) {
            return new ResponseEntity<>("Added",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEntry(@RequestBody JournalEntry journalEntry,@PathVariable ObjectId id) {
        if(journalEntryService.updateEntry(journalEntry,id)) {
            return new ResponseEntity<>("Updated",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEntry(@PathVariable ObjectId id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(journalEntryService.deleteEntry(id, auth.getName())) {
            return new ResponseEntity<>("Updated",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}