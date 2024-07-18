package io.ljunggren.tracking.webservice.response.dhl;

import java.util.List;

import io.ljunggren.dhl.api.response.Error;
import io.ljunggren.dhl.api.response.TrackingResponse;
import io.ljunggren.tracking.webservice.model.parcel.DhlParcel;
import io.ljunggren.tracking.webservice.model.parcel.Parcel;
import io.ljunggren.tracking.webservice.response.ResponseChain;

public class DhlErrorResponse extends ResponseChain {

    @Override
    public void execute(List<Parcel> parcels, Object response) {
        if (parcels.get(0) instanceof DhlParcel == false) {
            nextChain.execute(parcels, response);
            return;
        }
        TrackingResponse dhlResponse = (TrackingResponse) response;
        if (dhlResponse.getError() == null) {
            nextChain.execute(parcels, response);
            return;
        }
        setErrors(parcels, dhlResponse);
    }

    private void setErrors(List<Parcel> parcels, TrackingResponse response) {
        parcels.forEach(parcel -> setError(parcel, response.getError()));
    }
    
    private void setError(Parcel parcel, Error error) {
        // Error(responseCode=429, message={"status":429,"title":"Too Many Requests","detail":"Too many requests within defined time period, please try again later."})
        parcel.setStatus(String.valueOf(error.getResponseCode()));
        parcel.setMessage(error.getMessage());
    }

}
