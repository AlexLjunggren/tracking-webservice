package io.ljunggren.tracking.webservice.util;

import io.ljunggren.tracking.webservice.Service;

public class TrackingNumberUtils {

    public static Service getService(String trackingNumber) {
        if (trackingNumber == null) {
            return Service.UNKNOWN;
        }
        if (FedexUtils.isValidTrackingNumber(trackingNumber)) {
            return Service.FEDEX;
        }
        if (UpsUtils.isValidTrackingNumber(trackingNumber)) {
            return Service.UPS;
        }
        if (DhlUtils.isValidTrackingNumber(trackingNumber)) {
            return Service.DHL;
        }
        if (UspsUtils.isValidTrackingNumber(trackingNumber)) {
            return Service.USPS;
        }
        return Service.UNKNOWN;
    }
    
}
