package com.example.mobmech.controller;

import com.example.mobmech.dto.*;
import com.example.mobmech.entity.ServiceRequest;
import com.example.mobmech.entity.User;
import com.example.mobmech.security.JwtUtil;
import com.example.mobmech.service.ServiceRequestService;
import com.example.mobmech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerCustomer(
            @RequestBody RegisterRequest request) {

        return userService.registerCustomer(request);

    }
    @Autowired
    private ServiceRequestService serviceRequestService;

    @GetMapping("/{customerId}/dashboard")
    public CustomerDashboardDTO getCustomerDashboard(@PathVariable Long customerId) {

        return serviceRequestService.getCustomerDashboard(customerId);
    }
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequestDTO request) {

        User user = userService.login(
                request.getEmail(),
                request.getPassword());

        String token = jwtUtil.generateToken(user);

        return new AuthResponse(
                token,
                user.getRole(),
                user.getId(),
                user.getName(),
                user.getEmail(),
                null
        );
    }

    @PutMapping("{id}/location")
    public User updateLocation(@PathVariable Long id,
                               @RequestBody LocationDTO locationDTO) {

        return userService.updateLocation(id, locationDTO);
    }

    @GetMapping("/{id}/nearby-mechanics")
    public List<NearbyMechanicDTO> getNearbyMechanics(@PathVariable Long id) {
        return userService.getNearbyMechanics(id);
    }

    @PostMapping("/rate")
    public ResponseEntity<Map<String, String>> rateMechanic(
            @RequestBody RatingDTO dto) {

        serviceRequestService.rateMechanic(
                dto.getRequestId(),
                (int) dto.getRating()
        );

        return ResponseEntity.ok(
                Map.of("message", "Rating submitted")
        );
    }

    @PutMapping("/request/{requestId}/cancel")
    public ServiceRequest cancelRequest(
            @PathVariable Long requestId) {

        return serviceRequestService.cancelRequest(requestId);
    }

}