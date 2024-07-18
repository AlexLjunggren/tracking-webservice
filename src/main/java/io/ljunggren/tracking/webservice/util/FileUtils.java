package io.ljunggren.tracking.webservice.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

import io.ljunggren.tracking.webservice.property.WebserviceProperties;

public class FileUtils extends io.ljunggren.fileUtils.FileUtils {

    public static File convertToFile(MultipartFile multipartFile) throws IOException {
        Path path = Paths.get(WebserviceProperties.getFileTmpDirectory(), multipartFile.getOriginalFilename());
        File file = new File(path.toString());
        org.apache.commons.io.FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());
        file.deleteOnExit();
        return file;
    }
    
}
