package io.ljunggren.tracking.webservice.workflow;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import io.ljunggren.report.generator.excel.ExcelGenerator;

@Component
public class GenerateExcel extends WorkflowChain {

    @Override
    public void execute(WorkflowData data) throws Exception {
        generateExcel(data);
        nextChain.execute(data);
    }
    
    private void generateExcel(WorkflowData data) {
        Workbook workbook = new ExcelGenerator(data.getParcels()).generate();
        data.setWorkbook(workbook);
    }

}
