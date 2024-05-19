package com.sacidpak.courier.tracking.service.distance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValhallaDistanceCalculatorService implements DistanceCalculatorService {

    @Override
    public Double calculateDistance(Double firstLatitude, Double firstLongitude, Double secondLatitude, Double secondLongitude) {
        return null;
    }

}
