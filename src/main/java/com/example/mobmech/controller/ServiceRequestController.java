package com.example.mobmech.controller;

import com.example.mobmech.dto.ServiceRequestDTO;
import com.example.mobmech.dto.TrackingDTO;
import com.example.mobmech.entity.ServiceRequest;
import com.example.mobmech.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/request")

public class ServiceRequestController {

    @Autowired
    private ServiceRequestService serviceRequestService;

    @PostMapping("/create")
    public ServiceRequest createRequest(@RequestBody ServiceRequestDTO dto) {
        return serviceRequestService.createRequest(dto);
    }


    @PutMapping("/{id}/complete")
    public ServiceRequest completeRequest(@PathVariable Long id){
        return serviceRequestService.completeRequest(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<ServiceRequest> getCustomerRequests( @PathVariable Long customerId) {

        return serviceRequestService.getCustomerRequests(customerId);
    }

    @GetMapping("/mechanic/{mechanicId}")
        public List<ServiceRequest> getMechanicRequests( @PathVariable Long mechanicId) {

        return serviceRequestService.getMechanicRequests(mechanicId);
    }

    @GetMapping("/{id}")
    public ServiceRequest getRequest(@PathVariable Long id) {
        return serviceRequestService.getRequest(id);
    }

    @GetMapping("/{requestId}/tracking")
    public TrackingDTO getTracking(@PathVariable Long requestId) {

        return serviceRequestService.getTracking(requestId);
    }
}
