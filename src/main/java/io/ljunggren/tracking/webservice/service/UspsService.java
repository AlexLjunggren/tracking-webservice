package io.ljunggren.tracking.webservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.ljunggren.tracking.webservice.model.parcel.Parcel;
import io.ljunggren.tracking.webservice.property.WebserviceProperties;
import io.ljunggren.tracking.webservice.util.Progress;
import io.ljunggren.usps.api.UspsApi;
import io.ljunggren.usps.api.tracking.response.TrackingResponse;

@Service
public class UspsService extends TrackingService {
    
    private UspsApi uspsApi;

    public UspsService() {
        this.uspsApi = new UspsApi(
                WebserviceProperties.getUspsEnvironment(), 
                WebserviceProperties.getUspsUsername());
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public TrackingResponse track(String trackingNumber) throws Exception {
        return uspsApi.track(trackingNumber);
    }
    
    @Override
    public List<Parcel> trackParcels(List<Parcel> parcels) throws Exception {
        Progress progress = new Progress("Processing", parcels.size());
        for (Parcel parcel: parcels) {
            progress.log();
            process(parcel);
        }
        return parcels;
    }
    
    private void process(Parcel parcel) {
        try {
            TrackingResponse response = uspsApi.track(trim(parcel.getTrackingNumber()));
            processResponse(parcel, response);
        } catch (Exception e) {
            parcel.setStatus("Error");
            parcel.setMessage(e.getMessage());
        }
    }

}
