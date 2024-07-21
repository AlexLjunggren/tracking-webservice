package io.ljunggren.tracking.webservice.service;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.ljunggren.csvParser.Parser;
import io.ljunggren.tracking.webservice.model.ParsedCSV;
import io.ljunggren.tracking.webservice.util.FileUtils;

@Service
public class FileService {

    public ParsedCSV parse(MultipartFile multipartFile) throws Exception {
        File file = null;
        try {
            file = FileUtils.convertToFile(multipartFile);
            Parser parser = new Parser().firstRowIsHeader();
            List<String> headers = parser.parseHeaders(file);
            List<List<String>> data = parser.parse(file);
            return ParsedCSV.builder()
                    .headers(headers)
                    .data(data)
                    .build();
        } catch (Exception e) {
            throw new Exception("Unable to parse CSV");
        } finally {
            if (file != null) {
                file.delete();
            }
        }
    }
    
}
