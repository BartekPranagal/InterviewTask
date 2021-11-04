package com.pranagal.bartek.luxmedinterviewbackend.model.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ShipmentDataResponse {

    private Long id;
    private String email;
    private String address;
    private String phoneNumber;

}
