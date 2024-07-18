package io.ljunggren.tracking.webservice.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.ljunggren.fedex.api.tracking.response.ScanEvent;

public class FedexUtilsTest {
    
    @Test
    public void sortScanEventsTest() throws ParseException {
        ScanEvent scanEvent1 = new ScanEvent();
        scanEvent1.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01"));
        ScanEvent scanEvent2 = new ScanEvent();
        scanEvent2.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-03"));
        ScanEvent scanEvent3 = new ScanEvent();
        scanEvent3.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-04"));
        ScanEvent scanEvent4 = new ScanEvent();
        scanEvent4.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-02"));
        List<ScanEvent> scanEvents = Arrays.asList(new ScanEvent[] {
                scanEvent1, scanEvent2, scanEvent3, scanEvent4
        });
        List<ScanEvent> expected = Arrays.asList(new ScanEvent[] {
                scanEvent1, scanEvent4, scanEvent2, scanEvent3
        });
        FedexUtils.sortScanEvents(scanEvents);
        assertEquals(expected, scanEvents);
    }

    @Test
    public void sortScanEventsDescTest() throws ParseException {
        ScanEvent scanEvent1 = new ScanEvent();
        scanEvent1.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01"));
        ScanEvent scanEvent2 = new ScanEvent();
        scanEvent2.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-03"));
        ScanEvent scanEvent3 = new ScanEvent();
        scanEvent3.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-04"));
        ScanEvent scanEvent4 = new ScanEvent();
        scanEvent4.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-02"));
        List<ScanEvent> scanEvents = Arrays.asList(new ScanEvent[] {
                scanEvent1, scanEvent2, scanEvent3, scanEvent4
        });
        List<ScanEvent> expected = Arrays.asList(new ScanEvent[] {
                scanEvent3, scanEvent2, scanEvent4, scanEvent1
        });
        FedexUtils.sortScanEventsDesc(scanEvents);
        assertEquals(expected, scanEvents);
    }
    
    @Test
    public void isValidTrackingNumberTest() {
        assertTrue(FedexUtils.isValidTrackingNumber("9622001900001818342400741056306991"));
        assertTrue(FedexUtils.isValidTrackingNumber("9611020987654312345672"));
        assertTrue(FedexUtils.isValidTrackingNumber("61299998820821171811"));
        assertTrue(FedexUtils.isValidTrackingNumber("041441760228964"));
    }

    @Test
    public void isValidTrackingNumberFalseTest() {
        assertFalse(FedexUtils.isValidTrackingNumber(null));
        assertFalse(FedexUtils.isValidTrackingNumber(""));
        assertFalse(FedexUtils.isValidTrackingNumber("ABC123"));
    }

}
