package io.ljunggren.tracking.webservice.response;

import java.util.List;

import io.ljunggren.tracking.webservice.model.parcel.Parcel;

public class NullResponse extends ResponseChain {

    @Override
    public void execute(List<Parcel> parcels, Object response) {
        if (response != null) {
            nextChain.execute(parcels, response);
            return;
        }
        setError(parcels);
    }
    
    private void setError(List<Parcel> parcels) {
        parcels.forEach(parcel -> parcel.setStatus("Null response"));
    }

}
