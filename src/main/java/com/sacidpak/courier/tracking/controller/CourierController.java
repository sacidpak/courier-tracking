package com.sacidpak.courier.tracking.controller;

import com.sacidpak.courier.tracking.data.request.CourierLocationRequest;
import com.sacidpak.courier.tracking.service.CourierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/couriers")
public class CourierController {

    public final CourierService courierService;

    @PostMapping("/location")
    public ResponseEntity<Void> logLocation(@Valid @RequestBody CourierLocationRequest LocationRequest) {
        courierService.processLocation(LocationRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/total-distance/{courierId}")
    public ResponseEntity<Object> getTotalDistance(@PathVariable String courierId) {
        return ResponseEntity.ok(courierService.getTotalTravelDistance(courierId));
    }
}
