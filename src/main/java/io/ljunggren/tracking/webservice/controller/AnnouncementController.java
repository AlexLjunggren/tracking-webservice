package io.ljunggren.tracking.webservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.ljunggren.tracking.webservice.service.AnnouncementService;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController extends AbstractController {
    
    @Autowired
    private AnnouncementService announcementService;

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<String> parse() throws JsonProcessingException {
        try {
            List<String> announcements = announcementService.getAnnouncements();
            return okResponse(announcements);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }
    
}
