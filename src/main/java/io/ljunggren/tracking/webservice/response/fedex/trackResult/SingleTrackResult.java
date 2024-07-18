package io.ljunggren.tracking.webservice.response.fedex.trackResult;

import java.util.List;

import io.ljunggren.fedex.api.tracking.response.TrackResult;

public class SingleTrackResult extends TrackResultChain {

    @Override
    public TrackResult getSingle(List<TrackResult> trackResults) {
        if (trackResults.size() == 1) {
            return trackResults.get(0);
        }
        return nextChain.getSingle(trackResults);
    }

}
