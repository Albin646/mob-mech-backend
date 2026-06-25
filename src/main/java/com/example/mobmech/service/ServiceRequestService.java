package com.example.mobmech.service;

import com.example.mobmech.dto.CustomerDashboardDTO;
import com.example.mobmech.dto.MechanicDashboardDTO;
import com.example.mobmech.dto.ServiceRequestDTO;
import com.example.mobmech.entity.Mechanic;
import com.example.mobmech.entity.ServiceRequest;
import com.example.mobmech.entity.User;
import com.example.mobmech.repository.MechanicRepository;
import com.example.mobmech.repository.ServiceRequestRepository;
import com.example.mobmech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceRequestService {

    @Autowired
    private ServiceRequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    public ServiceRequest createRequest(ServiceRequestDTO dto){

        User customer = userRepository.findById(dto.getCustomerId()).orElseThrow(() -> new RuntimeException("User not found"));

        ServiceRequest request = new ServiceRequest();

        request.setVehicleType(dto.getVehicleType());
        request.setProblemDescription(dto.getProblemDescription());
        request.setLocation(dto.getLocation());

        request.setStatus("PENDING");
        request.setCustomer(customer);

        return requestRepository.save(request);
    }

    public List<ServiceRequest> getPendingRequests() {
        return requestRepository.findByStatus("PENDING");
    }

    @Autowired
    private MechanicRepository mechanicRepository;

    public ServiceRequest acceptRequest(Long requestId, Long mechanicId) {

        ServiceRequest request = requestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));

        Mechanic mechanic = mechanicRepository.findById(mechanicId).orElseThrow(() -> new RuntimeException("Mechanic not found"));


        request.setMechanic(mechanic);
        request.setStatus("ACCEPTED");

        return requestRepository.save(request);
    }

    public ServiceRequest completeRequest(Long requestId) {

        ServiceRequest request = requestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus("COMPLETED");

        return requestRepository.save(request);
    }


    public List<ServiceRequest> getCustomerRequests(Long customerId) {
        return requestRepository.findByCustomerId(customerId);
    }

    public List<ServiceRequest> getMechanicRequests(Long mechanicId) {
        return requestRepository.findByMechanicId(mechanicId);
    }

    public  CustomerDashboardDTO getCustomerDashboard(Long customerId) {

        long total = requestRepository.countByCustomerId(customerId);

        long pending = requestRepository.countByCustomerIdAndStatus(customerId, "PENDING");

        long accepted = requestRepository.countByCustomerIdAndStatus(customerId, "ACCEPTED");

        long completed = requestRepository.countByCustomerIdAndStatus(customerId, "COMPLETED");


        return new CustomerDashboardDTO(
                total,
                pending,
                accepted,
                completed
        );
    }

    public MechanicDashboardDTO getMechanicDashboard(Long mechanicId){

        long assignedJobs = requestRepository.countByMechanicId(mechanicId);

        long completedJobs = requestRepository.countByMechanicIdAndStatus(mechanicId, "MECHANIC");

        Mechanic mechanic = mechanicRepository.findById(mechanicId).orElseThrow(() -> new RuntimeException("Mechanic not found"));

        return new MechanicDashboardDTO(
                assignedJobs,
                completedJobs,
                mechanic.getRating()
        );
    }

    public ServiceRequest getRequest(Long id) {
        return requestRepository.findById(id).orElseThrow(() -> new RuntimeException("Request not found"));
    }
}
