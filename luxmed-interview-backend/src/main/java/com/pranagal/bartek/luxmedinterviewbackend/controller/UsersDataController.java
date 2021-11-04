package com.pranagal.bartek.luxmedinterviewbackend.controller;

import com.pranagal.bartek.luxmedinterviewbackend.model.dto.BasicUserDataRequest;
import com.pranagal.bartek.luxmedinterviewbackend.model.dto.BasicUserDataResponse;
import com.pranagal.bartek.luxmedinterviewbackend.model.dto.ShipmentAddressRequest;
import com.pranagal.bartek.luxmedinterviewbackend.model.dto.ShipmentDataResponse;
import com.pranagal.bartek.luxmedinterviewbackend.service.UsersDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UsersDataController {

        //api/users?query=12
    private final UsersDataService usersDataService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BasicUserDataResponse> getUserShippingAdresses(@RequestParam(required = false) String query){
        log.info("calling getUserShippingAdresses()");
        return usersDataService.getBasicUsersData(query);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicUserDataResponse addUser(@RequestBody BasicUserDataRequest request){
        return usersDataService.saveNewUser(request);
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicUserDataResponse updateUser(@RequestBody BasicUserDataRequest request, @PathVariable Long userId){
        return usersDataService.updateUser(userId, request);
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.DELETE)
    public BasicUserDataResponse deleteUser(@PathVariable Long userId){
        return usersDataService.deleteUser(userId);
    }

    @RequestMapping(path = "/{userId}/shipments",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShipmentDataResponse> getShipmentDataForUser(@PathVariable Long userId){
        return usersDataService.getAllShipmentDataForUser(userId);
    }

    @RequestMapping(path = "/{userId}/shipments", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public ShipmentDataResponse createShipmentDataForUser(@PathVariable Long userId, @RequestBody ShipmentAddressRequest request){
        return usersDataService.createShipmentDataForUser(userId, request);
    }

    @RequestMapping(path = "/{userId}/shipments/{shipmentId}", method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
    public ShipmentDataResponse updateShipmentDataForUser(@PathVariable Long userId, @PathVariable Long shipmentId, @RequestBody ShipmentAddressRequest request){
        return usersDataService.updateShipmentDataForUser(userId, shipmentId, request);
    }

    @RequestMapping(path = "/{userId}/shipments/{shipmentId}", method = RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ShipmentDataResponse deleteShipmentDataForUser(@PathVariable Long userId, @PathVariable Long shipmentId){
        return usersDataService.deleteShipmentDataForUser(userId, shipmentId);
    }
}
