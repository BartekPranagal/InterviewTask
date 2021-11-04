package com.pranagal.bartek.luxmedinterviewbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
public class BasicUserDataResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String personalId;

}
