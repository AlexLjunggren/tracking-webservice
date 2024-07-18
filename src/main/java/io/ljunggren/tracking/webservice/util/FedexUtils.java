package io.ljunggren.tracking.webservice.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import io.ljunggren.fedex.api.tracking.response.ScanEvent;

public class FedexUtils {

    public static void sortScanEvents(List<ScanEvent> scanEvents) {
        sortScanEvents(scanEvents, true);
    }
    
    public static void sortScanEventsDesc(List<ScanEvent> scanEvents) {
        sortScanEvents(scanEvents, false);
    }
    
    private static void sortScanEvents(List<ScanEvent> scanEvents, boolean ascending) {
        if (CollectionUtils.isEmpty(scanEvents)) {
            return;
        }
        Collections.sort(scanEvents, new Comparator<ScanEvent>() {
            @Override
            public int compare(ScanEvent event1, ScanEvent event2) {
                return ascending ?
                        event1.getDate().compareTo(event2.getDate()) :
                        event2.getDate().compareTo(event1.getDate());
            }
        });
    }
    
    public static boolean isValidTrackingNumber(String trackingNumber) {
        if (trackingNumber == null) {
            return false;
        }
        List<Integer> lengths = Arrays.asList(new Integer[] {34, 22, 20, 15, 12});
        for (Integer length: lengths) {
            String regex = String.format("^[0-9]{%d}$", length);
            if (trackingNumber.matches(regex)) {
                return true;
            }
        }
        return false;
    }
    
}
