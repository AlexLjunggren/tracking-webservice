package io.ljunggren.tracking.webservice.response;

import java.util.List;

import io.ljunggren.tracking.webservice.model.parcel.Parcel;

public abstract class ResponseChain {
    
    protected ResponseChain nextChain;
    
    public ResponseChain nextChain(ResponseChain nextChain) {
        this.nextChain = nextChain;
        return this;
    }

    public abstract void execute(List<Parcel> parcels, Object response);
    
}
