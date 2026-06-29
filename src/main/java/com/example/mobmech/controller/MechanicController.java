package com.example.mobmech.controller;

import com.example.mobmech.dto.*;
import com.example.mobmech.entity.Mechanic;
import com.example.mobmech.entity.ServiceRequest;
import com.example.mobmech.security.JwtUtil;
import com.example.mobmech.service.ServiceRequestService;
import com.example.mobmech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.mobmech.entity.User;

import java.util.List;

@RestController
@RequestMapping("/api/mechanic")
public class MechanicController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Mechanic registerMechanic(
            @RequestBody MechanicRegisterRequest request ){

        return userService.registerMechanic(request);
    }

    @Autowired
    private ServiceRequestService serviceRequestService;

    @GetMapping("/{mechanicId}/dashboard")
    public MechanicDashboardDTO getDashboard(@PathVariable Long mechanicId) {

        return serviceRequestService.getMechanicDashboard(mechanicId);
    }

    @GetMapping("/search")
    public List<Mechanic> searchMechanics (@RequestParam String specialization) {
        return userService.searchMechanics(specialization);
    }

    @GetMapping("/pending-requests")
    public List<ServiceRequest> getPendingRequests() {

        return serviceRequestService.getPendingRequests();
    }

    @PutMapping("{mechanicId}/accept/{requestId}")
    public ServiceRequest acceptRequest(@PathVariable Long mechanicId, @PathVariable Long requestId) {

        return serviceRequestService.acceptRequest(requestId, mechanicId);
    }

    @PutMapping("{id}/location")
    public User updateLocation(@PathVariable Long id,
                               @RequestBody LocationDTO locationDTO) {
        return userService.updateLocation(id, locationDTO);
    }

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequestDTO request) {

        User user = userService.login(
                request.getEmail(),
                request.getPassword());

        String token = jwtUtil.generateToken(user);

        Long mechanicId = userService.getMechanicIdByUserId(user.getId());

        return new AuthResponse(
                token,
                user.getRole(),
                user.getId(),
                user.getName(),
                user.getEmail(),
                mechanicId
        );
    }


}
