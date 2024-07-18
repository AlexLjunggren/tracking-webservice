package io.ljunggren.tracking.webservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import io.ljunggren.tracking.webservice.model.parcel.Parcel;
import io.ljunggren.tracking.webservice.response.CatchAllResponse;
import io.ljunggren.tracking.webservice.response.NullResponse;
import io.ljunggren.tracking.webservice.response.ResponseChain;
import io.ljunggren.tracking.webservice.response.dhl.DhlDataResponse;
import io.ljunggren.tracking.webservice.response.dhl.DhlErrorResponse;
import io.ljunggren.tracking.webservice.response.fedex.FedexDataResponse;
import io.ljunggren.tracking.webservice.response.fedex.FedexErrorResponse;
import io.ljunggren.tracking.webservice.response.ups.UpsDataResponse;
import io.ljunggren.tracking.webservice.response.usps.UspsDataResponse;

public abstract class TrackingService {
    
    abstract public <T> T track(String trackingNumber) throws Exception;
    abstract public List<Parcel> trackParcels(List<Parcel> parcels) throws Exception;
    
    protected void processResponse(Parcel parcel, Object response) {
        List<Parcel> parcels = Arrays.asList(new Parcel[] {parcel});
        processResponse(parcels, response);
    }
    
    protected void processResponse(List<Parcel> parcels, Object response) {
        ResponseChain chain = 
                new NullResponse().nextChain(
                new UpsDataResponse().nextChain(
                new DhlErrorResponse().nextChain(
                new DhlDataResponse().nextChain(
                new UspsDataResponse().nextChain(
                new FedexErrorResponse().nextChain(
                new FedexDataResponse().nextChain(
                new CatchAllResponse()
        )))))));
        chain.execute(parcels, response);
    }
    
    public <T> T parcelFromString(String trackingNumber, Class<T> clazz) {
        T t = newInstance(clazz);
        ((Parcel) t).setTrackingNumber(trackingNumber);
        return t;
    }
    
    public <T> List<Parcel> parcelsFromArray(String[] trackingNumbers, Class<T> clazz) {
        return Arrays.asList(trackingNumbers).stream()
                .filter(trackingNumber -> !StringUtils.isBlank(trackingNumber))
                .map(trackingNumber -> (Parcel) parcelFromString(trackingNumber, clazz))
                .collect(Collectors.toList());
    }
    
    private <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    protected String trim(String value) {
        return value == null ? null : value.trim();
    }
    
}
