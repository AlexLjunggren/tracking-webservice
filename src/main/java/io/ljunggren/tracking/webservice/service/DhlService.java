package io.ljunggren.tracking.webservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import io.ljunggren.dhl.api.DhlApi;
import io.ljunggren.dhl.api.response.TrackingResponse;
import io.ljunggren.streamUtils.StreamUtils;
import io.ljunggren.tracking.webservice.model.parcel.Parcel;
import io.ljunggren.tracking.webservice.property.WebserviceProperties;
import io.ljunggren.tracking.webservice.util.Progress;

@Service
public class DhlService extends TrackingService {
    
    private DhlApi dhlApi;
    private final int BATCH_SIZE = 3;
    
    public DhlService() {
        this.dhlApi = new DhlApi(
                WebserviceProperties.getDhlEnvironment(), 
                WebserviceProperties.getDhlApiKey());
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public TrackingResponse track(String trackingNumber) throws Exception {
        return dhlApi.track(trackingNumber);
    }
    
    @Override
    public List<Parcel> trackParcels(List<Parcel> parcels) throws IOException {
        Progress progress = new Progress("Processing batch", parcels.size(), BATCH_SIZE);
        long lastBatchStart = -1L;
        for (int i = 0; i < parcels.size(); i += BATCH_SIZE) {
            lastBatchStart = throttle(lastBatchStart);
            progress.log();
            List<Parcel> batch = new ArrayList<Parcel>(StreamUtils.getSlice(parcels, i, i + BATCH_SIZE - 1));
            processBatch(batch);
        }
        return parcels;
    }
    
    private long throttle(long lastBatchStart) {
        while (System.currentTimeMillis() - lastBatchStart < 1000) {
            // delay
        }
        return System.currentTimeMillis();
    }
    
    private void processBatch(List<Parcel> parcels) throws IOException {
        String[] trackingNumbers = parcels.stream()
                .map(parcel -> trim(parcel.getTrackingNumber()))
                .toArray(String[]::new);
        TrackingResponse response = dhlApi.track(trackingNumbers);
        processResponse(parcels, response);
    }

}
