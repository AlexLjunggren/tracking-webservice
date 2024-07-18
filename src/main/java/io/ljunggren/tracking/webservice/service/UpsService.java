package io.ljunggren.tracking.webservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.ljunggren.tracking.webservice.model.parcel.Parcel;
import io.ljunggren.tracking.webservice.property.WebserviceProperties;
import io.ljunggren.tracking.webservice.util.Progress;
import io.ljunggren.ups.api.UpsApi;
import io.ljunggren.ups.api.UpsResponse;
import io.ljunggren.ups.api.model.AuthToken;

@Service
public class UpsService extends TrackingService {

    private UpsApi upsApi;
    
    public UpsService() {
        this.upsApi = new UpsApi(
                WebserviceProperties.getUpsEnvironment(),
                WebserviceProperties.getUpsOauthClientId(), 
                WebserviceProperties.getUpsOauthClientSecret(),
                WebserviceProperties.getUpsAccountNumber());
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public UpsResponse track(String trackingNumber) throws Exception {
        return upsApi.track(trackingNumber);
    }
    
    @Override
    public List<Parcel> trackParcels(List<Parcel> parcels) throws Exception {
        AuthToken authToken = upsApi.getAuthToken();
        Progress progress = new Progress("Processing", parcels.size());
        for (Parcel parcel: parcels) {
            progress.log();
            process( parcel, authToken);
        }
        return parcels;
    }
    
    private void process(Parcel parcel, AuthToken authToken) {
        try {
            UpsResponse response = upsApi.track(trim(parcel.getTrackingNumber()), authToken);
            processResponse(parcel, response);
        } catch (Exception e) {
            parcel.setStatus("Error");
            parcel.setMessage(e.getMessage());
        }
    }

}
