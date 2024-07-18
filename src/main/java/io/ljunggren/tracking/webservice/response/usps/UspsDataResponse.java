package io.ljunggren.tracking.webservice.response.usps;

import java.util.List;

import io.ljunggren.tracking.webservice.model.parcel.Parcel;
import io.ljunggren.tracking.webservice.model.parcel.UspsParcel;
import io.ljunggren.tracking.webservice.response.ResponseChain;
import io.ljunggren.usps.api.tracking.response.TrackingResponse;

public class UspsDataResponse extends ResponseChain {

    @Override
    public void execute(List<Parcel> parcels, Object response) {
        if (parcels.get(0) instanceof UspsParcel == false) {
            nextChain.execute(parcels, response);
            return;
        }
        TrackingResponse uspsResponse = (TrackingResponse) response;
        if (uspsResponse.getTrackInfo() == null) {
            nextChain.execute(parcels, response);
            return;
        }
        
    }

}
