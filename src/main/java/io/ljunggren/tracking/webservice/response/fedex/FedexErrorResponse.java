package io.ljunggren.tracking.webservice.response.fedex;

import java.util.List;
import java.util.stream.Collectors;

import io.ljunggren.fedex.api.tracking.response.TrackingResponse;
import io.ljunggren.tracking.webservice.model.parcel.FedexParcel;
import io.ljunggren.tracking.webservice.model.parcel.Parcel;
import io.ljunggren.tracking.webservice.response.ResponseChain;

public class FedexErrorResponse extends ResponseChain {

    @Override
    public void execute(List<Parcel> parcels, Object response) {
        if (parcels.get(0) instanceof FedexParcel == false) {
            nextChain.execute(parcels, response);
            return;
        }
        TrackingResponse fedexResponse = (TrackingResponse) response;
        if (fedexResponse.getErrors() == null) {
            nextChain.execute(parcels, response);
            return;
        }
        setErrors(parcels, fedexResponse);
    }
    
    private void setErrors(List<Parcel> parcels, TrackingResponse response) {
        String errorCodes = response.getErrors().stream()
                .map(error -> error.getCode())
                .collect(Collectors.joining(", "));
        String errorDescriptions = response.getErrors().stream()
                .map(error -> error.getMessage())
                .collect(Collectors.joining(", "));
        parcels.forEach(parcel -> {
            parcel.setStatus(errorCodes);
            parcel.setMessage(errorDescriptions);
        });
    }

}
