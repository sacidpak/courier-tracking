package com.sacidpak.courier.tracking.data.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CourierLocationRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -822598785798507605L;

    @NotBlank(message = "{not.blank.message}")
    private String courierId;

    @NotNull(message = "{not.null.message}")
    private Double latitude;

    @NotNull(message = "{not.null.message}")
    private Double longitude;
}
