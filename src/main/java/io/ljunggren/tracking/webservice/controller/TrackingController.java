package io.ljunggren.tracking.webservice.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.ljunggren.sanitizer.Sanitizer;
import io.ljunggren.tracking.webservice.Service;
import io.ljunggren.tracking.webservice.model.BatchTrackingRequest;
import io.ljunggren.tracking.webservice.model.TrackingRequest;
import io.ljunggren.tracking.webservice.model.parcel.DhlParcel;
import io.ljunggren.tracking.webservice.model.parcel.FedexParcel;
import io.ljunggren.tracking.webservice.model.parcel.Parcel;
import io.ljunggren.tracking.webservice.model.parcel.UpsParcel;
import io.ljunggren.tracking.webservice.model.parcel.UspsParcel;
import io.ljunggren.tracking.webservice.service.DhlService;
import io.ljunggren.tracking.webservice.service.FedexService;
import io.ljunggren.tracking.webservice.service.TrackingService;
import io.ljunggren.tracking.webservice.service.UpsService;
import io.ljunggren.tracking.webservice.service.UspsService;
import io.ljunggren.tracking.webservice.service.ValidationService;
import io.ljunggren.tracking.webservice.service.WorkflowService;
import io.ljunggren.tracking.webservice.util.DhlUtils;
import io.ljunggren.tracking.webservice.util.FedexUtils;
import io.ljunggren.tracking.webservice.util.UpsUtils;
import io.ljunggren.tracking.webservice.workflow.WorkflowData;

@RestController
@RequestMapping("/api/tracking")
public class TrackingController extends AbstractController {
    
    private static Logger logger = LoggerFactory.getLogger(TrackingController.class);

    @Autowired
    private ValidationService validationService;
    
    @Autowired
    private FedexService fedexService;
    
    @Autowired 
    private DhlService dhlService;
    
    @Autowired
    private UpsService upsService;
    
    @Autowired
    private UspsService uspsService;
    
    @Autowired
    private WorkflowService workflowService;
    
    @CrossOrigin
    @PostMapping()
    public ResponseEntity<String> track(@RequestBody TrackingRequest request) throws JsonProcessingException {
        try {
            Set<String> errors = validationService.validate(request);
            if (!errors.isEmpty()) {
                return badRequestResponse(errors);
            }
            sanitize(request);
            TrackingService trackingService = getTrackingService(request.getService());
            Class<? extends Parcel> clazz = getParcelClass(request.getService());
            Parcel parcel = trackingService.parcelFromString(request.getTrackingNumber(), clazz);
            List<Parcel> parcels = Arrays.asList(new Parcel[] {parcel});
            WorkflowData data = WorkflowData.builder()
                    .parcels(parcels)
                    .build();
            workflowService.process(data);
            return okResponse(data.getParcels().get(0));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(e.getMessage());
        }
    }
    
    @CrossOrigin
    @PostMapping("/batch")
    public ResponseEntity<String> batch(@RequestBody BatchTrackingRequest request) throws JsonProcessingException {
        try {
            Set<String> errors = validationService.validate(request);
            if (!errors.isEmpty()) {
                return badRequestResponse(errors);
            }
            sanitize(request);
            TrackingService trackingService = getTrackingService(request.getService());
            Class<? extends Parcel> clazz = getParcelClass(request.getService());
            List<Parcel> parcels = trackingService.parcelsFromArray(request.getTrackingNumbers(), clazz);
            WorkflowData data = WorkflowData.builder()
                    .email(request.getEmail())
                    .parcels(parcels)
                    .filename(generateFilename(request.getService()))
                    .build();
            workflowService.asyncProcess(data);
            return okResponse();
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }
    
    @CrossOrigin
    @PostMapping("/raw")
    public ResponseEntity<String> raw(@RequestBody TrackingRequest request) throws JsonProcessingException {
        try {
            Set<String> errors = validationService.validate(request);
            if (!errors.isEmpty()) {
                return badRequestResponse(errors);
            }
            sanitize(request);
            TrackingService trackingService = getTrackingService(request.getService(), request.getTrackingNumber());
            Object response = trackingService.track(request.getTrackingNumber());
            return okResponse(response);
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }
    
    private void sanitize(Object object) {
        Sanitizer sanitizer = new Sanitizer(object);
        sanitizer.sanitize();
    }
    
    private TrackingService getTrackingService(Service service) throws Exception {
        return getTrackingService(service, null);
    }
     
    private TrackingService getTrackingService(Service service, String trackingNumber) throws Exception {
        switch (service) {
        case FEDEX: return fedexService;
        case DHL: return dhlService;
        case UPS: return upsService;
        case USPS: return uspsService;
        case AUTO: return getTrackingService(determineService(trackingNumber), trackingNumber);
        default: throw new Exception("Unimplemented service");
        }
    }
    
    private Service determineService(String trackingNumber) {
        logger.info("Attempting to auto detect " + trackingNumber);
        if (trackingNumber == null) {
            return Service.UNKNOWN;
        }
        if (FedexUtils.isValidTrackingNumber(trackingNumber)) {
            return Service.FEDEX;
        }
        if (UpsUtils.isValidTrackingNumber(trackingNumber)) {
            return Service.UPS;
        }
        if (DhlUtils.isValidTrackingNumber(trackingNumber)) {
            return Service.DHL;
        }
        return Service.UNKNOWN;
    }
    
    private Class<? extends Parcel> getParcelClass(Service service) throws Exception {
        switch (service) {
        case FEDEX: return FedexParcel.class;
        case DHL: return DhlParcel.class;
        case UPS: return UpsParcel.class;
        case USPS: return UspsParcel.class;
        default: throw new Exception("Unimplemented service");
        }
    }
    
    private String generateFilename(Service service) {
        return service.name().toLowerCase() + "-tracking.csv";
    }

}
