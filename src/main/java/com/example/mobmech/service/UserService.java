package com.example.mobmech.service;

import com.example.mobmech.dto.LocationDTO;
import com.example.mobmech.dto.MechanicRegisterRequest;
import com.example.mobmech.dto.NearbyMechanicDTO;
import com.example.mobmech.dto.RegisterRequest;
import com.example.mobmech.entity.Mechanic;
import com.example.mobmech.entity.ServiceRequest;
import com.example.mobmech.entity.User;
import com.example.mobmech.repository.MechanicRepository;
import com.example.mobmech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerCustomer(RegisterRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());

        user.setRole(User.Role.CUSTOMER);

        return userRepository.save(user);
    }

    @Autowired
    private MechanicRepository mechanicRepository;

    public Mechanic registerMechanic(
            MechanicRegisterRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        user.setRole(User.Role.MECHANIC);

        User savedUser = userRepository.save(user);

        Mechanic mechanic = new Mechanic();

        mechanic.setExperience(request.getExperience());
        mechanic.setSpecialization(request.getSpecialization());

        mechanic.setLatitude(
                request.getLatitude() != null ? request.getLatitude() : 0.0
        );

        mechanic.setLongitude(
                request.getLongitude() != null ? request.getLongitude() : 0.0
        );

        mechanic.setUser(savedUser);

        return mechanicRepository.save(mechanic);

    }


    public List<Mechanic> searchMechanics(String specialization) {
        return mechanicRepository.findBySpecialization(specialization);
    }

    public Long getMechanicIdByUserId(Long userId) {
        return mechanicRepository.findByUserId(userId)
                .map(Mechanic::getId)
                .orElseThrow(() -> new RuntimeException("Mechanic profile not found"));
    }

    public User login(String email, String password) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)){
            throw new RuntimeException("Invalid Password");
        }

        return user;
    }

    public User updateLocation(Long userId, LocationDTO locationDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        user.setLatitude(locationDTO.getLatitude());
        user.setLongitude(locationDTO.getLongitude());

        return userRepository.save(user);
    }

    public List<NearbyMechanicDTO> getNearbyMechanics(Long customerId) {


        User customer = userRepository.findById(customerId).orElseThrow(() -> new RuntimeException("User not found!"));

        List<Mechanic> mechanics = mechanicRepository.findAll();

        List<NearbyMechanicDTO> result = new ArrayList<>();


        for (Mechanic mechanic : mechanics) {

            double customerLat = customer.getLatitude();
            double customerLon = customer.getLongitude();

            double mechanicLat = mechanic.getUser().getLatitude();
            double mechanicLon = mechanic.getUser().getLongitude();

            double distance = calculateDistance(
                    customerLat,
                    customerLon,
                    mechanicLat,
                    mechanicLon

            );

            NearbyMechanicDTO dto = new NearbyMechanicDTO(
                    mechanic.getId(),
                    mechanic.getUser().getName(),
                    mechanic.getSpecialization(),
                    distance,
                    mechanic.getUser().getLatitude(),
                    mechanic.getUser().getLongitude()
            );
            result.add(dto);
        }

        return result;
    }
        private double calculateDistance(
        double lat1,
        double lon1,
        double lat2,
        double lon2) {

            final int R = 6371;

            double latDistance = Math.toRadians(lat2 - lat1);
            double lonDistance = Math.toRadians(lon2 - lon1);

            double a =
                    Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                            + Math.cos(Math.toRadians(lat1))
                            * Math.cos(Math.toRadians(lat2))
                            * Math.sin(lonDistance / 2)
                            * Math.sin(lonDistance / 2);

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            return R * c;

    }

}