package com.pranagal.bartek.luxmedinterviewbackend.controller;

import com.pranagal.bartek.luxmedinterviewbackend.model.dto.BasicUserDataResponse;
import com.pranagal.bartek.luxmedinterviewbackend.service.UsersDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;


@WebMvcTest(UsersDataController.class)
class UsersDataControllerIT {

    @MockBean
    private UsersDataService usersDataService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnUsersData() throws Exception {
        when(usersDataService.getBasicUsersData(null)).thenReturn(
                List.of(BasicUserDataResponse.builder().firstName("Jan").lastName("Nowak").personalId("123123123").build(),
                        BasicUserDataResponse.builder().firstName("Piotr").lastName("Kowalski").personalId("321321321").build(),
                        BasicUserDataResponse.builder().firstName("Andrzej").lastName("Kmicic").personalId("565565565").build())
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", equalTo(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", equalTo("Jan")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", equalTo("Nowak")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].personalId", equalTo("123123123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", equalTo("Piotr")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName", equalTo("Kowalski")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].personalId", equalTo("321321321")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].firstName", equalTo("Andrzej")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].lastName", equalTo("Kmicic")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].personalId", equalTo("565565565")))
                .andDo(MockMvcResultHandlers.print());



    }

}

