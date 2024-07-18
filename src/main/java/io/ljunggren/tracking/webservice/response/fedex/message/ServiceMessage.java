package io.ljunggren.tracking.webservice.response.fedex.message;

import io.ljunggren.fedex.api.tracking.response.ServiceCommitMessage;
import io.ljunggren.fedex.api.tracking.response.TrackResult;

public class ServiceMessage extends MessageChain {

    @Override
    public String getMessage(TrackResult trackResult) {
        String serviceMessage = getServiceMessage(trackResult);
        if (serviceMessage != null) {
            return serviceMessage;
        }
        return nextChain.getMessage(trackResult);
    }
    
    private String getServiceMessage(TrackResult trackResult) {
        ServiceCommitMessage serviceCommitMessage = trackResult.getServiceCommitMessage();
        if (serviceCommitMessage == null) {
            return null;
        }
        return serviceCommitMessage.getMessage();
    }

}
