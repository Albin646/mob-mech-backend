package com.example.mobmech.repository;

import com.example.mobmech.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

    List<ServiceRequest> findByStatus(String status);

    List<ServiceRequest> findByCustomerId(Long customerId);

    List<ServiceRequest> findByMechanicId(Long mechanicId);

   long countByCustomerId(Long customerId);

   long countByCustomerIdAndStatus(Long customerId, String status);

   Long countByMechanicId(Long mechanicId);

   Long countByMechanicIdAndStatus(Long mechanicId, String status);
}
