package com.sample.journalApp.services;

import com.sample.journalApp.models.AppUser;
import com.sample.journalApp.models.JournalEntry;
import com.sample.journalApp.repositories.AppUserRepository;
import com.sample.journalApp.repositories.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppUserRepository appUserRepository;

    public boolean registerEntry(JournalEntry journalEntry,String username) {
        AppUser user = appUserService.getUser(username);
        if(user != null) {
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            appUserRepository.save(user);
            return true;
        }
        return false;
    }

    public List<JournalEntry> fetchEntries() {
        return journalEntryRepository.findAll();
    }

    public JournalEntry fetchEntry(ObjectId id) {
        return journalEntryRepository.findById(id).orElse(null);
    }

    public boolean updateEntry(JournalEntry journalEntry,ObjectId id) {
        JournalEntry oldEntry = journalEntryRepository.findById(id).orElse(null);
        if(oldEntry == null) {
            return false;
        }
        oldEntry.setTitle(journalEntry.getTitle() != null && !journalEntry.getTitle().equals("") ? journalEntry.getTitle() : oldEntry.getTitle());
        oldEntry.setContent(journalEntry.getContent() != null && !journalEntry.getContent().equals("") ? journalEntry.getContent() : oldEntry.getContent());
        journalEntryRepository.save(oldEntry);
        return true;
    }

    public boolean deleteEntry(ObjectId id,String username) {
        Optional<JournalEntry> journalEntryOpt = journalEntryRepository.findById(id);
        AppUser user = appUserService.getUser(username);
        if(journalEntryOpt.isEmpty() || user == null) {
            return false;
        }
        JournalEntry journalEntry = journalEntryOpt.get();
        boolean removed = user.getJournalEntries().removeIf(entry -> entry.getId().equals(journalEntry.getId()));
        if (removed) {
            appUserRepository.save(user);
            journalEntryRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
