package io.ljunggren.tracking.webservice.workflow;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.ljunggren.tracking.webservice.property.WebserviceProperties;
import io.ljunggren.tracking.webservice.util.FileUtils;

@Component
public class GenerateFile extends WorkflowChain {

    @Override
    public void execute(WorkflowData data) throws Exception {
        generate(data);
        nextChain.execute(data);
    }
    
    private void generate(WorkflowData data) throws IOException {
        Path path = FileUtils.createFile(generateOutputPath(data.getFilename()), data.getCsv());
        data.setOutputPath(path);
    }

    private Path generateOutputPath(String filename) {
        String date = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        String generatedFilename = String.format("%s_%s", date, filename);
        Path path = Paths.get(WebserviceProperties.getFileOutputDirectory(), generatedFilename);
        return path;
    }
    
}
