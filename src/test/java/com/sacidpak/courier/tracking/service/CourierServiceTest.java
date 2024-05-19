package com.sacidpak.courier.tracking.service;

import com.sacidpak.courier.tracking.config.EnvironmentConfig;
import com.sacidpak.courier.tracking.data.request.CourierLocationRequest;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourierServiceTest {

    @InjectMocks
    private CourierService courierService;

    @Mock
    private StoreService storeService;

    @Mock
    private CourierLocationRepository locationRepository;

    @Mock
    private CourierDistanceRepository distanceRepository;

    @Mock
    private CourierStoreLogRepository storeLogRepository;

    @Mock
    private EnvironmentConfig environmentConfig;

    @Mock
    private Map<DistanceCalculateType, DistanceCalculatorService> distanceStrategyMap;

    @Test
    public void shouldSaveLocationData_whenCallProcessLocation() {
        //given
        var locationRequest = CourierLocationRequest.builder()
                .courierId("55")
                .latitude(40.9923307)
                .longitude(29.1244229).build();

        var store = Store.builder()
                .code("ATA34")
                .latitude(40.9923307)
                .longitude(29.1244229).build();

        var courierLocation = CourierLocation.builder()
                .courierId(locationRequest.getCourierId())
                .latitude(locationRequest.getLatitude())
                .longitude(locationRequest.getLongitude())
                .build();
        courierLocation.setCreatedAt(LocalDateTime.now());

        var courierDistance = CourierDistance.builder()
                .courierId("55")
                .totalDistance(BigDecimal.ZERO).build();

        var distanceCalculatorService = mock(DistanceCalculatorService.class);

        //when
        when(storeService.getStores()).thenReturn(List.of(store));
        when(locationRepository.save(any(CourierLocation.class))).thenReturn(courierLocation);
        when(locationRepository.findTopByCourierIdOrderByCreatedAtDesc(anyString())).thenReturn(Optional.of(courierLocation));
        when(distanceRepository.findByCourierId(anyString())).thenReturn(Optional.of(courierDistance));
        when(storeLogRepository.existsByCourierIdAndStoreCodeAndCreatedAtAfter(anyString(), anyString(), any())).thenReturn(false);
        when(distanceCalculatorService.calculateDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble())).thenReturn(0.05);
        when(distanceStrategyMap.get(any(DistanceCalculateType.class))).thenReturn(distanceCalculatorService);
        when(environmentConfig.getDistanceStrategy()).thenReturn(DistanceCalculateType.OFFLINE);

        courierService.processLocation(locationRequest);

        //then
        verify(locationRepository, times(1)).save(any(CourierLocation.class));
        verify(distanceRepository, times(1)).save(any(CourierDistance.class));
        verify(storeLogRepository, times(1)).save(any(CourierStoreLog.class));
    }

    @Test
    public void shouldReturnCourierDistance_whenCallGetTotalTravelDistance() {
        //given
        var courierId = "55";
        var courierDistance = CourierDistance.builder()
                .courierId(courierId)
                .totalDistance(BigDecimal.ZERO).build();
        //when
        when(distanceRepository.findByCourierId(courierId)).thenReturn(Optional.of(courierDistance));

        var response = courierService.getTotalTravelDistance(courierId);

        //then
        assertNotNull(response);
        assertEquals(courierId, response.getCourierId());
        assertEquals(BigDecimal.ZERO, response.getTotalDistance());
    }

    @Test
    public void shouldThrowNotFoundException_whenNotFoundCourierDistanceEntity() {
        //given
        var courierId = "55";
        var expectedMessage = MessageUtil.getMessage("error.entity.not.found");

        //when
        when(distanceRepository.findByCourierId(courierId)).thenReturn(Optional.empty());

        var exception = assertThrows(NotFoundException.class, () -> courierService.getTotalTravelDistance(courierId));

        //then
        assertTrue(exception.getMessage().contains(expectedMessage));
    }


}
