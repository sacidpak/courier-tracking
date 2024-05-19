package com.sacidpak.courier.tracking.service;

import com.sacidpak.courier.tracking.config.EnvironmentConfig;
import com.sacidpak.courier.tracking.data.request.CourierLocationRequest;
import com.sacidpak.courier.tracking.data.response.CourierTotalDistanceResponse;
import com.sacidpak.courier.tracking.domain.entity.CourierDistance;
import com.sacidpak.courier.tracking.domain.entity.CourierLocation;
import com.sacidpak.courier.tracking.domain.entity.CourierStoreLog;
import com.sacidpak.courier.tracking.domain.repository.CourierDistanceRepository;
import com.sacidpak.courier.tracking.domain.repository.CourierLocationRepository;
import com.sacidpak.courier.tracking.domain.repository.CourierStoreLogRepository;
import com.sacidpak.courier.tracking.dto.Store;
import com.sacidpak.courier.tracking.enums.DistanceCalculateType;
import com.sacidpak.courier.tracking.exception.NotFoundException;
import com.sacidpak.courier.tracking.service.distance.DistanceCalculatorService;
import com.sacidpak.courier.tracking.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CourierService {

    private final StoreService storeService;

    private final CourierLocationRepository locationRepository;

    private final CourierDistanceRepository distanceRepository;

    private final CourierStoreLogRepository storeLogRepository;

    private final EnvironmentConfig environmentConfig;

    private final Map<DistanceCalculateType, DistanceCalculatorService> distanceStrategyMap;

    private static final double RADIUS = 0.1; //km

    @Transactional
    public void processLocation(CourierLocationRequest locationRequest) {
        updateTotalDistance(locationRequest);

        var location = saveLastLocation(locationRequest);

        storeService.getStores().forEach(store -> {
            var distance = calculateDistance(locationRequest.getLatitude(), locationRequest.getLongitude(),
                    store.getLatitude(), store.getLongitude());
            if (distance < RADIUS) {
                logEntrance(location, store);
            }
        });
    }

    public CourierTotalDistanceResponse getTotalTravelDistance(String courierId) {
        return distanceRepository.findByCourierId(courierId)
                .map(courierDistance -> CourierTotalDistanceResponse.builder()
                        .totalDistance(courierDistance.getTotalDistance())
                        .courierId(courierId)
                        .build())
                .orElseThrow(() -> new NotFoundException(MessageUtil.getMessage("error.entity.not.found")));
    }

    private CourierLocation saveLastLocation(CourierLocationRequest locationRequest) {
        var location = CourierLocation.builder()
                .courierId(locationRequest.getCourierId())
                .latitude(locationRequest.getLatitude())
                .longitude(locationRequest.getLongitude())
                .build();

        return locationRepository.save(location);
    }

    private void logEntrance(CourierLocation location, Store store) {
        var courierId = location.getCourierId();
        var storeCode = store.getCode();
        var oneMinuteAgo = location.getCreatedAt().minusMinutes(1);

        var hasRecentEntry = storeLogRepository.existsByCourierIdAndStoreCodeAndCreatedAtAfter(courierId, storeCode, oneMinuteAgo);

        if (!hasRecentEntry) {
            var storeLog = new CourierStoreLog();
            storeLog.setCourierId(courierId);
            storeLog.setStoreCode(storeCode);
            storeLog.setCreatedAt(location.getCreatedAt());

            storeLogRepository.save(storeLog);
        }
    }

    private void updateTotalDistance(CourierLocationRequest locationRequest) {
        var distance = 0.0;
        var courierId = locationRequest.getCourierId();
        var lastLocationOpt = locationRepository.findTopByCourierIdOrderByCreatedAtDesc(courierId);

        if (lastLocationOpt.isPresent()) {
            var lastLocation = lastLocationOpt.get();
            distance = calculateDistance(lastLocation.getLatitude(), lastLocation.getLongitude(),
                                         locationRequest.getLatitude(), locationRequest.getLongitude());
        }

        var courierDistance = distanceRepository.findByCourierId(courierId).orElse(new CourierDistance());
        courierDistance.setCourierId(courierId);
        courierDistance.setTotalDistance(courierDistance.getTotalDistance().add(BigDecimal.valueOf(distance)));

        distanceRepository.save(courierDistance);
    }

    private Double calculateDistance(Double firstLatitude, Double firstLongitude, Double secondLatitude, Double secondLongitude) {
        var strategy = distanceStrategyMap.get(environmentConfig.getDistanceStrategy());
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown distance calculate type: " + environmentConfig.getDistanceStrategy());
        }

        return strategy.calculateDistance(firstLatitude, firstLongitude, secondLatitude, secondLongitude);
    }
}
