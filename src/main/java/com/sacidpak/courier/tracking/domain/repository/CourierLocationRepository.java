package com.sacidpak.courier.tracking.domain.repository;

import com.sacidpak.courier.tracking.domain.entity.CourierLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierLocationRepository extends JpaRepository<CourierLocation, Long> {
    Optional<CourierLocation> findTopByCourierIdOrderByCreatedAtDesc(String courierId);
}
