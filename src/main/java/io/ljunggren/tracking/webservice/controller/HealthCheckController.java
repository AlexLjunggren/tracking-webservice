package io.ljunggren.tracking.webservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController extends AbstractController {

    @GetMapping
    public ResponseEntity<String> healthCheck() throws JsonProcessingException {
        return okResponse("running");
    }
    
}
