package com.sacidpak.courier.tracking.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courier_store_log", indexes = {@Index(name = "ix_courier_store_log_courier_id", columnList = "courier_id"),
        @Index(name = "ix_courier_store_log_store_code", columnList = "store_code")})
public class CourierStoreLog extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -4911721704471259172L;

    @NotBlank(message = "{not.blank.message}")
    @Column(name = "courier_id", nullable = false, length = 30)
    private String courierId;

    @NotBlank(message = "{not.blank.message}")
    @Column(name = "store_code", nullable = false, length = 100)
    private String storeCode;
}
