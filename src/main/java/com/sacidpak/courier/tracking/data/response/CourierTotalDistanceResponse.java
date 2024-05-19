package com.sacidpak.courier.tracking.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierTotalDistanceResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 990076232436291860L;

    private String courierId;

    private BigDecimal totalDistance;
}
