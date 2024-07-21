package io.ljunggren.tracking.webservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.ljunggren.tracking.webservice.exception.BadRequestException;
import io.ljunggren.tracking.webservice.model.ParsedCSV;
import io.ljunggren.tracking.webservice.service.FileService;

@RestController
@RequestMapping("/api/file")
public class FileController extends AbstractController {
    
    @Autowired
    private FileService fileService;
    
    @CrossOrigin
    @PostMapping("/parse")
    public ResponseEntity<String> csv(@RequestParam("file") MultipartFile multipartFile) throws JsonProcessingException {
        try {
            ParsedCSV parsedCSV = fileService.parse(multipartFile);
            return okResponse(parsedCSV);
        } catch (BadRequestException e) {
            return badRequestResponse(e.getMessage());
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

}
