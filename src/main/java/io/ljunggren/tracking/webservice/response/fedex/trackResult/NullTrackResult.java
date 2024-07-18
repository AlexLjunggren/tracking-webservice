package io.ljunggren.tracking.webservice.response.fedex.trackResult;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import io.ljunggren.fedex.api.tracking.response.TrackResult;

public class NullTrackResult extends TrackResultChain {

    @Override
    public TrackResult getSingle(List<TrackResult> trackResults) {
        if (CollectionUtils.isEmpty(trackResults)) {
            return new TrackResult();
        }
        return nextChain.getSingle(trackResults);
    }

}
