package com.sacidpak.courier.tracking.domain.repository;

import com.sacidpak.courier.tracking.domain.entity.CourierDistance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierDistanceRepository extends JpaRepository<CourierDistance,Long> {

    Optional<CourierDistance> findByCourierId(String courierId);

}
