package io.ljunggren.tracking.webservice.response.fedex.message;

import io.ljunggren.fedex.api.tracking.response.TrackResult;

public abstract class MessageChain {

    protected MessageChain nextChain;
    
    public MessageChain nextChain(MessageChain nextChain) {
        this.nextChain = nextChain;
        return this;
    }
    
    public abstract String getMessage(TrackResult trackResult);
    
}
