package com.sacidpak.courier.tracking.domain.repository;

import com.sacidpak.courier.tracking.domain.entity.CourierStoreLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface CourierStoreLogRepository extends JpaRepository<CourierStoreLog, Long> {
    boolean existsByCourierIdAndStoreCodeAndCreatedAtAfter(String courierId, String storeCode, LocalDateTime timestamp);
}
