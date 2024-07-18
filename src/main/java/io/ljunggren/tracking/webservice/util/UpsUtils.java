package io.ljunggren.tracking.webservice.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import io.ljunggren.ups.api.model.tracking.response.DeliveryDate;

public class UpsUtils {

    public static void sortDeliveryDates(List<DeliveryDate> deliveryDates) {
        sortDeliveryDates(deliveryDates, true);
    }
    
    public static void sortDeliveryDatesDesc(List<DeliveryDate> deliveryDates) {
        sortDeliveryDates(deliveryDates, false);
    }
    
    private static void sortDeliveryDates(List<DeliveryDate> deliveryDates, boolean ascending) {
        if (CollectionUtils.isEmpty(deliveryDates)) {
            return;
        }
        Collections.sort(deliveryDates, new Comparator<DeliveryDate>() {
            @Override
            public int compare(DeliveryDate deliveryDate1, DeliveryDate deliveryDate2) {
                return ascending ?
                        deliveryDate1.getDate().compareTo(deliveryDate2.getDate()) :
                        deliveryDate2.getDate().compareTo(deliveryDate1.getDate());
            }
        });
    }
    
    public static boolean isValidTrackingNumber(String trackingNumber) {
        if (trackingNumber == null) {
            return false;
        }
        List<Integer> lengths = Arrays.asList(new Integer[] {18});
        for (Integer length: lengths) {
            String regex = String.format("^[0-9]{%d}$", length);
            if (trackingNumber.matches(regex)) {
                return true;
            }
        }
        if (trackingNumber.matches("^(1Z)[0-9A-Z]{16}$")) {
            return true;
        }
        return false;
    }
    
}
