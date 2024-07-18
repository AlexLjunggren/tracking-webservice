package io.ljunggren.tracking.webservice.model.parcel;

public interface Parcel {

    public String getTrackingNumber();
    public void setTrackingNumber(String trackingNumber);
    public void setService(String service);
    public void setStatus(String status);
    public void setMessage(String message);
    
}
