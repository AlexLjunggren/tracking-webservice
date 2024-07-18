package io.ljunggren.tracking.webservice.response.fedex.trackResult;

import java.util.List;
import java.util.stream.Collectors;

import io.ljunggren.fedex.api.tracking.response.ScanEvent;
import io.ljunggren.fedex.api.tracking.response.TrackResult;

public class MultipleTrackResult extends TrackResultChain {

    @Override
    public TrackResult getSingle(List<TrackResult> trackResults) {
        List<ScanEvent> scanEvents = getAllScanEvents(trackResults);
        TrackResult trackResult = getTrackResultWithLatestScan(trackResults);
        trackResult.setScanEvents(scanEvents);
        return trackResult;
    }
    
    private List<ScanEvent> getAllScanEvents(List<TrackResult> trackResults) {
        return trackResults.stream()
                .flatMap(trackResult -> trackResult.getScanEvents().stream())
                .collect(Collectors.toList());
    }
    
    private TrackResult getTrackResultWithLatestScan(List<TrackResult> trackResults) {
        return trackResults.stream().max((result1, result2) -> 
                getLatestScanEvent(result1).getDate().compareTo(getLatestScanEvent(result2).getDate())).get();
    }

    private ScanEvent getLatestScanEvent(TrackResult trackResult) {    
        return trackResult.getScanEvents().stream()
                .max((event1, event2) -> event1.getDate().compareTo(event2.getDate())).get();
    }
    
}
