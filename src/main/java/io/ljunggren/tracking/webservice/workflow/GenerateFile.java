package io.ljunggren.tracking.webservice.workflow;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import io.ljunggren.tracking.webservice.property.WebserviceProperties;

@Component
public class GenerateFile extends WorkflowChain {

    @Override
    public void execute(WorkflowData data) throws Exception {
        generateExcel(data);
        nextChain.execute(data);
    }
    
    private void generateExcel(WorkflowData data) throws IOException {
        Path path = generateOutputPath(data);
        FileOutputStream outputStream = new FileOutputStream(path.toString());
        Workbook workbook = data.getWorkbook();
        nameSheet(workbook);
        workbook.write(outputStream);
        data.setOutputPath(path);
    }
    
    private void nameSheet(Workbook workbook) {
        workbook.setSheetName(0, "Tracking Results");
    }
    
    private Path generateOutputPath(WorkflowData data) {
        String date = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        String generatedFilename = String.format("%s_%s-tracking.xlsx", date, data.getService().name().toLowerCase());
        return Paths.get(WebserviceProperties.getFileOutputDirectory(), generatedFilename);
    }
    
}
