package com.sacidpak.courier.tracking.config;

import com.sacidpak.courier.tracking.enums.DistanceCalculateType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class EnvironmentConfig {

    @Value("#{'${distance.strategy}'.toUpperCase()}")
    private DistanceCalculateType distanceStrategy;
}
