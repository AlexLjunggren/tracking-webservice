package io.ljunggren.tracking.webservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import io.ljunggren.tracking.webservice.workflow.EndChain;
import io.ljunggren.tracking.webservice.workflow.GenerateExcel;
import io.ljunggren.tracking.webservice.workflow.GenerateFile;
import io.ljunggren.tracking.webservice.workflow.ProcessDhl;
import io.ljunggren.tracking.webservice.workflow.ProcessFedex;
import io.ljunggren.tracking.webservice.workflow.ProcessUps;
import io.ljunggren.tracking.webservice.workflow.ProcessUsps;
import io.ljunggren.tracking.webservice.workflow.SanitizeParcels;
import io.ljunggren.tracking.webservice.workflow.SendNotification;
import io.ljunggren.tracking.webservice.workflow.WorkflowChain;
import io.ljunggren.tracking.webservice.workflow.WorkflowData;

@Service
public class WorkflowService {
    
    @Autowired
    private SanitizeParcels sanitizeParcels;
    
    @Autowired
    private ProcessFedex processFedex;
    
    @Autowired
    private ProcessUps processUps;
    
    @Autowired
    private ProcessUsps processUsps;
    
    @Autowired
    private ProcessDhl processDhl;
    
    @Autowired
    private GenerateExcel generateExcel;
    
    @Autowired
    private GenerateFile generateFile;
    
    @Autowired
    private SendNotification sendNotification;
    
    @Autowired
    private EndChain endChain;
    
    @Async
    public void asyncProcess(WorkflowData data) {
        try {
            WorkflowChain chain = 
                    processFedex.nextChain(
                    processUps.nextChain(
                    processUsps.nextChain(
                    processDhl.nextChain(
                    sanitizeParcels.nextChain(
                    generateExcel.nextChain(
                    generateFile.nextChain(
                    sendNotification.nextChain(
                    endChain
            ))))))));
            chain.execute(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void process(WorkflowData data) throws Exception {
        WorkflowChain chain = 
                processFedex.nextChain(
                processUps.nextChain(
                processUsps.nextChain(
                processDhl.nextChain(
                sanitizeParcels.nextChain(
                endChain
        )))));
        chain.execute(data);
    }
    
}
