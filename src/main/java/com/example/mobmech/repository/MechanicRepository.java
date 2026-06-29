package com.example.mobmech.repository;

import com.example.mobmech.entity.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MechanicRepository extends JpaRepository<Mechanic, Long>{

    List<Mechanic> findBySpecialization(String specialization);

    Optional<Mechanic> findByUserId(Long userId);
}
