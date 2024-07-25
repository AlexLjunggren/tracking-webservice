package io.ljunggren.tracking.webservice.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.ljunggren.tracking.webservice.exception.BadRequestException;
import io.ljunggren.tracking.webservice.util.CSVUtils;
import io.ljunggren.tracking.webservice.util.FileUtils;
import ljunggren.io.excel.parser.Parser;
import ljunggren.io.excel.parser.model.SimpleWorkbook;

@Service
public class FileService {
    
    private static Logger logger = LoggerFactory.getLogger(FileService.class);

    public SimpleWorkbook parse(MultipartFile multipartFile) throws Exception {
        File file = null;
        try {
            file = FileUtils.convertToFile(multipartFile);
            String fileExtension = FileUtils.getFileExtension(file).toLowerCase();
            switch (fileExtension) {
            case "csv": 
                return parseCSV(file);
            case "xlsx":
                return parseExcel(file);
            default:
                logger.warn(String.format("Attempted to parse %s file", fileExtension));
                throw new BadRequestException(String.format("Application does not support %s file type", fileExtension));
            }
        } finally {
            if (file != null) {
                file.delete();
            }
        }
    }
    
    private SimpleWorkbook parseCSV(File file) throws Exception {
        return CSVUtils.csvToWorkbook(file);
    }
    
    private SimpleWorkbook parseExcel(File file) throws Exception {
        return new Parser().firstRowIsHeader().parse(file);
    }
    
}
