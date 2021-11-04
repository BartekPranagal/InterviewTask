package com.pranagal.bartek.luxmedinterviewfrontend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pranagal.bartek.luxmedinterviewfrontend.dto.ShipmentData;
import com.pranagal.bartek.luxmedinterviewfrontend.dto.UserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class BackendService {

    private final ObservableList<UserData> userData;
    private final ObservableList<ShipmentData> shipmentData;

    private final BackendClient backendClient;
    private final ObjectMapper objectMapper;
    private static BackendService INSTANCE;

    public static BackendService getInstance() {
        if (Objects.isNull(INSTANCE)) {
            BackendService.INSTANCE = new BackendService();
        }
        return INSTANCE;
    }

    private BackendService() {
        this.objectMapper = new ObjectMapper();
        this.backendClient = new BackendClient();
        ObservableList<UserData> tempUserData;
        try {
            tempUserData = FXCollections.observableList(getUserDataFromService(null));
        } catch (Exception e) {
            e.printStackTrace();
            tempUserData = FXCollections.observableList(new ArrayList<>());
        }
        this.userData = tempUserData;
        this.shipmentData = FXCollections.observableList(new ArrayList<>());
    }

    public ObservableList<UserData> search(String query) {
        try {
            ArrayList<UserData> userDataFromService = getUserDataFromService(query);
            this.userData.clear();
            this.userData.addAll(userDataFromService);
            return this.userData;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<UserData> getUserDataFromService(String query) throws IOException, InterruptedException {
        HttpResponse<String> response = backendClient.searchUsers(query);
        return objectMapper.readValue(response.body(), new TypeReference<>() {
        });
    }

    public ObservableList<ShipmentData> getShipmentsForUser(Long id) {
        try {
            HttpResponse<String> response = backendClient.getShipmentsForUser(id);
            this.shipmentData.clear();
            this.shipmentData.addAll(objectMapper.readValue(response.body(), new TypeReference<List<ShipmentData>>() {
            }));
            return shipmentData;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<UserData> addUser(String firstName, String lastName, String personalId) {
        try {
            HttpResponse<String> newUserWithId = backendClient.addNewUser(
                    objectMapper.writeValueAsString(UserData.builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .personalId(personalId)
                            .build()
                    ));
            UserData newUser = objectMapper.readValue(newUserWithId.body(), UserData.class);
            userData.add(newUser);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return userData;
    }

    public ObservableList<UserData> deleteUser(Long id) {
        try {
            this.backendClient.deleteUser(id);
            this.userData.removeIf(x -> x.getId().equals(id));
        } catch (Exception e){
            e.printStackTrace();
        }
        return userData;
    }

    public ObservableList<UserData> editUser(long id, String firstName, String lastName, String personalId) {
        try {
            UserData updatedElement = UserData.builder()
                    .id(id)
                    .firstName(firstName)
                    .lastName(lastName)
                    .personalId(personalId)
                    .build();

            backendClient.updateUser(id, objectMapper.writeValueAsString(updatedElement));
            UserData currentElement = this.userData.stream()
                    .filter(x -> x.getId().equals(id))
                    .findAny().orElseThrow();

            this.userData.set(this.userData.indexOf(currentElement), updatedElement);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return userData;
    }

    public void addShipmentAddress(Long userId, String email, String address, String phoneNumber) {
//        shipmentData.get(userId).add(ShipmentData.builder()
//                .id(shipmentData.get(userId).stream().mapToLong(ShipmentData::getId).max().orElse(0) + 1)
//                .email(email)
//                .address(address)
//                .phoneNumber(phoneNumber)
//                .build());
    }

    public void editShipmentAddress(Long userId, Long shipmentId, String newFirstName, String newLastName, String newPersonalId) {
//        Optional<ShipmentData> first = shipmentData.get(userId)
//                .stream()
//                .filter(x -> x.getId().equals(shipmentId))
//                .findFirst();
//        if (first.isPresent()) {
//            ShipmentData oldData = first.get();
//            shipmentData.get(userId).remove(oldData);
//            ShipmentData updatedData = ShipmentData.builder()
//                    .id(oldData.getId())
//                    .email(newFirstName)
//                    .address(newLastName)
//                    .phoneNumber(newPersonalId)
//                    .build();
//            shipmentData.get(userId)
//                    .add(updatedData);
//        }

    }

    public void deleteShipmentAddress(Long userId, Long shipmentId) {
//        shipmentData.get(userId).remove(shipmentData.get(userId).stream().filter(x -> x.getId().equals(shipmentId)).findAny().get());
    }
}
