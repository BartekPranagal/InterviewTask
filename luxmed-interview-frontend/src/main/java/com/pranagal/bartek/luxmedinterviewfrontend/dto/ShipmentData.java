package com.pranagal.bartek.luxmedinterviewfrontend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor //dwie ostatnie zeby dzialal ObjectMapper, trudny do wykrycia blad
public class ShipmentData {

    private Long id;
    private String email;
    private String address;
    private String phoneNumber;

}
