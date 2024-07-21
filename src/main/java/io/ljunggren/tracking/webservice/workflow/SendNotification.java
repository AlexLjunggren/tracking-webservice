package io.ljunggren.tracking.webservice.workflow;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.ljunggren.tracking.webservice.service.EmailService;

@Component
public class SendNotification extends WorkflowChain {
    
    private static Logger logger = LoggerFactory.getLogger(SendNotification.class);

    @Autowired
    private EmailService emailService;

    @Override
    public void execute(WorkflowData data) throws Exception {
        notify(data);
        nextChain.execute(data);
    }
    
    private void notify(WorkflowData data) throws MessagingException {
        emailService.sendEmail(
                data.getEmail(), 
                "Tracking Results", 
                "Tracking process complete. Please see attached results.",
                data.getOutputPath());
        logger.info("Completion email sent to " + data.getEmail());
    }
    
}
