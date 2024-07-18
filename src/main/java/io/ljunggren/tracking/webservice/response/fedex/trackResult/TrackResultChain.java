package io.ljunggren.tracking.webservice.response.fedex.trackResult;

import java.util.List;

import io.ljunggren.fedex.api.tracking.response.TrackResult;

public abstract class TrackResultChain {

    protected TrackResultChain nextChain;
    
    public TrackResultChain nextChain(TrackResultChain nextChain) {
        this.nextChain = nextChain;
        return this;
    }
    
    public abstract TrackResult getSingle(List<TrackResult> trackResults);
    
}
