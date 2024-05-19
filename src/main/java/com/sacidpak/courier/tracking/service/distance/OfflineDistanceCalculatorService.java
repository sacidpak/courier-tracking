package com.sacidpak.courier.tracking.service.distance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfflineDistanceCalculatorService implements DistanceCalculatorService {

    @Override
    public Double calculateDistance(Double firstLatitude, Double firstLongitude, Double secondLatitude, Double secondLongitude){
        double theta = firstLongitude - secondLongitude;
        double dist = Math.sin(deg2rad(firstLatitude)) * Math.sin(deg2rad(secondLatitude)) + Math.cos(deg2rad(firstLatitude))
                * Math.cos(deg2rad(secondLatitude)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 1.609344 * 100;

        return dist;
    }

    private Double deg2rad(Double deg) {
        return (deg * Math.PI / 180.0);
    }


    private Double rad2deg(Double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
