package io.ljunggren.tracking.webservice.workflow;

import org.springframework.stereotype.Component;

import io.ljunggren.reportGenerator.csv.CSVGenerator;

@Component
public class GenerateCSV extends WorkflowChain {

    @Override
    public void execute(WorkflowData data) throws Exception {
        generateCsv(data);
        nextChain.execute(data);
    }
    
    private void generateCsv(WorkflowData data) {
        String csv = new CSVGenerator(data.getParcels()).generate();
        data.setCsv(csv);
    }

}
