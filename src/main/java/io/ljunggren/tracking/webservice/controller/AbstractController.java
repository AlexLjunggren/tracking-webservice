package io.ljunggren.tracking.webservice.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.ljunggren.jsonUtils.JsonUtils;
import io.ljunggren.tracking.webservice.model.Message;

public class AbstractController {
    
    private String generateResponse(Object object) throws JsonProcessingException {
        return JsonUtils.objectToJson(object);
    }
    
    protected ResponseEntity<String> okResponse() {
        return ResponseEntity.noContent().build();
    }
    
    protected ResponseEntity<String> okResponse(String message) throws JsonProcessingException {
        Message response = new Message(message);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(generateResponse(response));
    }
    
    protected ResponseEntity<String> okResponse(Object object) throws JsonProcessingException {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(generateResponse(object));
    }
    
    protected ResponseEntity<String> badRequestResponse(Object object) throws JsonProcessingException {
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(generateResponse(object));
    }
    
    protected ResponseEntity<String> badRequestResponse(String message) throws JsonProcessingException {
        Message response = new Message(message);
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(generateResponse(response));
    }
    
    protected ResponseEntity<String> errorResponse(String message) throws JsonProcessingException {
        Message response = new Message(message);
        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(generateResponse(response));
    }
    
}
