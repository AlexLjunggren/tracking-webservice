package io.ljunggren.tracking.webservice.response.fedex.message;

import io.ljunggren.fedex.api.tracking.response.TrackResult;

public class CatchAllMessage extends MessageChain {

    @Override
    public String getMessage(TrackResult trackResult) {
        return null;
    }

}
