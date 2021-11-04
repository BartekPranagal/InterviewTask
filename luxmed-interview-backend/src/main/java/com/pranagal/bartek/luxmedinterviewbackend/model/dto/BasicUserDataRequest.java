package com.pranagal.bartek.luxmedinterviewbackend.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BasicUserDataRequest {
    private String firstName;
    private String lastName;
    private String personalId;

}
