package io.ljunggren.tracking.webservice.response;

import java.util.List;

import io.ljunggren.tracking.webservice.model.parcel.Parcel;

public class CatchAllResponse extends ResponseChain {

    @Override
    public void execute(List<Parcel> parcels, Object response) {
        parcels.forEach(parcel -> {
            parcel.setStatus("Error");
            parcel.setMessage("Unknown Response");
        });
    }

}
