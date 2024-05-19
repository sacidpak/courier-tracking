package com.sacidpak.courier.tracking.dto;

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
public class Store implements Serializable {

    @Serial
    private static final long serialVersionUID = -7319334750469371353L;

    private String name;

    private String code;

    private Double latitude;

    private Double longitude;

}
