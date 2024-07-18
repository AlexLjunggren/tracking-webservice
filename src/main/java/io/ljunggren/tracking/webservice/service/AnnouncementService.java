package io.ljunggren.tracking.webservice.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.ljunggren.tracking.webservice.property.WebserviceProperties;
import io.ljunggren.tracking.webservice.util.FileUtils;

@Service
public class AnnouncementService {

    private static Logger logger = LoggerFactory.getLogger(AnnouncementService.class);

    public List<String> getAnnouncements() {
        File directory = new File(WebserviceProperties.getAnnouncementDirectory());
        File[] files = directory.listFiles();
        return Arrays.stream(files)
                .map(file -> file.getAbsolutePath())
                .map(path -> readFile(path))
                .collect(Collectors.toList());
    }
    
    private String readFile(String path) {
        try {
            return FileUtils.readFile(path);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return "Unable to read file, " + path;
        }
    }
    
}
