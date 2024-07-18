package io.ljunggren.tracking.webservice.workflow;

import org.springframework.stereotype.Component;

@Component
public class EndChain extends WorkflowChain {

    @Override
    public void execute(WorkflowData data) throws Exception {
        // Do nothing
    }

}
