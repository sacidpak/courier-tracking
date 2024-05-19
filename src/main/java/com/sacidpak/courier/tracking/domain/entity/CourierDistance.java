package com.sacidpak.courier.tracking.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
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
@Entity
@Table(name = "courier_distance", uniqueConstraints = {@UniqueConstraint(name = "ix_unique_courier_distance_courier_id", columnNames = "courier_id")})
public class CourierDistance extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1641352634917011476L;

    @NotBlank(message = "{not.blank.message}")
    @Column(name = "courier_id", nullable = false, length = 30)
    private String courierId;

    @Builder.Default
    @Column(name = "total_distance", precision = 20, scale = 3)
    private BigDecimal totalDistance = BigDecimal.ZERO;
}
