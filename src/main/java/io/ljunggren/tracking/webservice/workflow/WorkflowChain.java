package io.ljunggren.tracking.webservice.workflow;

public abstract class WorkflowChain {
    
    protected WorkflowChain nextChain;
    
    public WorkflowChain nextChain(WorkflowChain nextChain) {
        this.nextChain = nextChain;
        return this;
    }
    
    public abstract void execute(WorkflowData data) throws Exception;

}
