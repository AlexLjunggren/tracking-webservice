package io.ljunggren.tracking.webservice.workflow;

import org.springframework.stereotype.Component;

import io.ljunggren.sanitizer.Sanitizer;

@Component
public class SanitizeParcels extends WorkflowChain {

    @Override
    public void execute(WorkflowData data) throws Exception {
        cleanse(data);
        nextChain.execute(data);
    }

    public void cleanse(WorkflowData data) {
        Sanitizer sanitizer = new Sanitizer(data.getParcels());
        sanitizer.sanitize();
    }
    
}
