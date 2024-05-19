package com.sacidpak.courier.tracking.service;

import com.sacidpak.courier.tracking.service.distance.OfflineDistanceCalculatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
public class OfflineDistanceCalculatorServiceTest {

    @InjectMocks
    private OfflineDistanceCalculatorService offlineDistanceCalculatorService;

    @Test
    public void shouldReturnCourierDistance_whenCallGetTotalTravelDistance() {
        //given
        var firstLatitude = 40.9923307;
        var firstLongitude = 29.1244229;
        var secondLongitude = 29.1304229;

        //when
        var response = offlineDistanceCalculatorService.calculateDistance(firstLatitude, firstLongitude, firstLatitude, secondLongitude);

        //then
        assertNotNull(response);
        assertEquals(response, 0.7288371782205179);
    }


}
