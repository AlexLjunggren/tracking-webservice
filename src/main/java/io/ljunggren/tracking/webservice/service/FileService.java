package io.ljunggren.tracking.webservice.service;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.ljunggren.csvParser.Parser;
import io.ljunggren.tracking.webservice.util.FileUtils;

@Service
public class FileService {

    public List<String> parse(MultipartFile multipartFile) throws Exception {
        File file = null;
        try {
            file = FileUtils.convertToFile(multipartFile);
            return parse(file);
        } finally {
            if (file != null) {
                file.delete();
            }
        }
    }
    
    private List<String> parse(File file) throws Exception {
        try {
            return new Parser().firstRowIsHeader().parse(file, String.class);
        } catch (Exception e) {
            throw new Exception("Unable to parse CSV");
        }
    }
    
}
