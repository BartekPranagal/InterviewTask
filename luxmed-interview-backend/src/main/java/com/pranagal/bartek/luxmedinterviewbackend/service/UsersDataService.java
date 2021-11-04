package com.pranagal.bartek.luxmedinterviewbackend.service;

import com.pranagal.bartek.luxmedinterviewbackend.model.dao.ShipmentAddressEntity;
import com.pranagal.bartek.luxmedinterviewbackend.model.dao.UserDataEntity;
import com.pranagal.bartek.luxmedinterviewbackend.model.dto.BasicUserDataRequest;
import com.pranagal.bartek.luxmedinterviewbackend.model.dto.BasicUserDataResponse;
import com.pranagal.bartek.luxmedinterviewbackend.model.dto.ShipmentAddressRequest;
import com.pranagal.bartek.luxmedinterviewbackend.model.dto.ShipmentDataResponse;
import com.pranagal.bartek.luxmedinterviewbackend.repository.ShipmentAddressRepository;
import com.pranagal.bartek.luxmedinterviewbackend.repository.UserDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersDataService {

    private final UserDataRepository userDataRepository;
    private final ShipmentAddressRepository shipmentAddressRepository;

    public List<BasicUserDataResponse> getBasicUsersData(String query) {
        return Optional.ofNullable(query)
                .map(param -> userDataRepository.searchByQuery(param))
                .orElse(userDataRepository.findAll())
                .stream()
                .map(entity -> convertUserEntityToDto(entity))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ShipmentDataResponse> getAllShipmentDataForUser(Long userId) {
        return userDataRepository.findById(userId)
                .map(userDataEntity -> userDataEntity.getShipmentAddresses())
                .map(addressesEntities -> addressesEntities.stream()
                        .map(this::convertShipmentEntityToDto)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new IllegalArgumentException("user with provided id not found"));
    }

    public BasicUserDataResponse saveNewUser(BasicUserDataRequest request) {
        UserDataEntity newEntity = new UserDataEntity();
        newEntity.setFirstName(request.getFirstName());
        newEntity.setLastName(request.getLastName());
        newEntity.setPersonalId(request.getPersonalId());
        newEntity = userDataRepository.save(newEntity);
        return convertUserEntityToDto(newEntity);
    }

    @Transactional
    public BasicUserDataResponse updateUser(Long id, BasicUserDataRequest request) {
        UserDataEntity existingEntity = userDataRepository.findById(id).orElseThrow();
        existingEntity.setFirstName(request.getFirstName());
        existingEntity.setLastName(request.getLastName());
        existingEntity.setPersonalId(request.getPersonalId());
        return convertUserEntityToDto(existingEntity);
    }

    public BasicUserDataResponse deleteUser(Long userId) {
        UserDataEntity elementToDelete = userDataRepository.findById(userId).orElseThrow();
        userDataRepository.delete(elementToDelete);
        return convertUserEntityToDto(elementToDelete);
    }


    @Transactional
    public ShipmentDataResponse createShipmentDataForUser(Long userId, ShipmentAddressRequest request) {
        UserDataEntity user = userDataRepository.findById(userId).orElseThrow();
        ShipmentAddressEntity newAddress = new ShipmentAddressEntity();
        newAddress.setUser(user);
        newAddress.setAddress(request.getAddress());
        newAddress.setEmail(request.getEmail());
        newAddress.setPhoneNumber(request.getPhoneNumber());

        ShipmentAddressEntity savedAddress = shipmentAddressRepository.save(newAddress);
        Optional.ofNullable(user.getShipmentAddresses()).ifPresentOrElse(
                x -> x.add(savedAddress),
                () -> user.setShipmentAddresses(new ArrayList<>(Collections.singletonList(savedAddress)))
        );
        return convertShipmentEntityToDto(savedAddress);
    }

    @Transactional
    public ShipmentDataResponse updateShipmentDataForUser(Long userId, Long shipmentId, ShipmentAddressRequest request) {
        ShipmentAddressEntity entityToUpdate = shipmentAddressRepository.findByIdAndUserId(shipmentId, userId).orElseThrow();
        entityToUpdate.setAddress(request.getAddress());
        entityToUpdate.setEmail(request.getEmail());
        entityToUpdate.setPhoneNumber(request.getPhoneNumber());
        return convertShipmentEntityToDto(entityToUpdate);
    }

    public ShipmentDataResponse deleteShipmentDataForUser(Long userId, Long shipmentId) {
        ShipmentAddressEntity entityToUpdate = shipmentAddressRepository.findByIdAndUserId(shipmentId, userId).orElseThrow();
        shipmentAddressRepository.delete(entityToUpdate);
        return convertShipmentEntityToDto(entityToUpdate);
    }

    private BasicUserDataResponse convertUserEntityToDto(UserDataEntity entity) {
        return BasicUserDataResponse.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .personalId(entity.getPersonalId())
                .id(entity.getId())
                .build();
    }

    private ShipmentDataResponse convertShipmentEntityToDto(ShipmentAddressEntity entity) {
        return ShipmentDataResponse.builder()
                .id(entity.getId())
                .address(entity.getAddress())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .build();
    }

}
