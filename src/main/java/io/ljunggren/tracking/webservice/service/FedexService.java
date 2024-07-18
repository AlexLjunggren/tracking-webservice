package io.ljunggren.tracking.webservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import io.ljunggren.fedex.api.FedexApi;
import io.ljunggren.fedex.api.factory.TrackingFactory;
import io.ljunggren.fedex.api.tracking.request.TrackingRequest;
import io.ljunggren.fedex.api.tracking.response.TrackingResponse;
import io.ljunggren.streamUtils.StreamUtils;
import io.ljunggren.tracking.webservice.model.parcel.Parcel;
import io.ljunggren.tracking.webservice.property.WebserviceProperties;
import io.ljunggren.tracking.webservice.util.Progress;

@Service
public class FedexService extends TrackingService {
    
    private FedexApi fedexApi;
    private final int BATCH_SIZE = 30;
    
    public FedexService() {
        this.fedexApi = new FedexApi(
                WebserviceProperties.getFedexEnvironment(), 
                WebserviceProperties.getFedexOauthClientId(), 
                WebserviceProperties.getFedexOauthClientSecret());
    }

    @Override
    @SuppressWarnings("unchecked")
    public TrackingResponse track(String trackingNumber) throws Exception {
        TrackingRequest request = TrackingFactory.basicRequest(trim(trackingNumber));
        String accessToken = fedexApi.authorize().getAccessToken();
        return fedexApi.track(request, accessToken);
    }
    
    @Override
    public List<Parcel> trackParcels(List<Parcel> parcels) throws IOException {
        String accessToken = fedexApi.authorize().getAccessToken();
        Progress progress = new Progress("Processing batch", parcels.size(), BATCH_SIZE);
        for (int i = 0; i < parcels.size(); i += BATCH_SIZE) {
            progress.log();
            List<Parcel> batch = new ArrayList<Parcel>(StreamUtils.getSlice(parcels, i, i + BATCH_SIZE - 1));
            processBatch(batch, accessToken);
        }
        return parcels;
    }
    
    private void processBatch(List<Parcel> parcels, String accessToken) throws IOException {
        String[] trackingNumbers = parcels.stream()
                .map(parcel -> trim(parcel.getTrackingNumber()))
                .toArray(String[]::new);
        TrackingRequest request = TrackingFactory.basicRequest(trackingNumbers);
        TrackingResponse response = fedexApi.track(request, accessToken);
        processResponse(parcels, response);
    }
    
}
