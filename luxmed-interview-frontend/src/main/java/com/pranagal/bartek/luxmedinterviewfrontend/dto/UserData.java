package com.pranagal.bartek.luxmedinterviewfrontend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    private Long id;
    private String firstName;
    private String lastName;
    private String personalId;

}
