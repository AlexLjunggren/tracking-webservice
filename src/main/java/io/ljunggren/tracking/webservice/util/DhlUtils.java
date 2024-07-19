package io.ljunggren.tracking.webservice.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import io.ljunggren.dhl.api.response.ShipmentEvent;

public class DhlUtils {

    public static void sortShipmentEvents(List<ShipmentEvent> events) {
        sortShipmentEvents(events, true);
    }
    
    public static void sortShipmentEventsDesc(List<ShipmentEvent> events) {
        sortShipmentEvents(events, false);
    }

    private static void sortShipmentEvents(List<ShipmentEvent> events, boolean ascending) {
        if (CollectionUtils.isEmpty(events)) {
            return;
        }
        Collections.sort(events, new Comparator<ShipmentEvent>() {
            @Override
            public int compare(ShipmentEvent shipmentEvent1, ShipmentEvent shipmentEvent2) {
                return ascending ?
                        shipmentEvent1.getTimestamp().compareTo(shipmentEvent2.getTimestamp()) :
                        shipmentEvent2.getTimestamp().compareTo(shipmentEvent1.getTimestamp());
            }
        });
    }
    
    public static boolean isValidTrackingNumber(String trackingNumber) {
        if (trackingNumber == null) {
            return false;
        }
        if (trackingNumber.matches("^[0-9]{10}$")) {
            return true;
        }
        return false;
    }
    
}
