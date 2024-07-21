package io.ljunggren.tracking.webservice.util;

public class UspsUtils {

    public static boolean isValidTrackingNumber(String trackingNumber) {
        if (trackingNumber == null) {
            return false;
        }
        if (trackingNumber.matches("^[0-9]{20}$")) {
            return true;
        }
        return false;
    }
    
}