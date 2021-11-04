package com.pranagal.bartek.luxmedinterviewbackend.repository;

import com.pranagal.bartek.luxmedinterviewbackend.model.dao.ShipmentAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShipmentAddressRepository extends JpaRepository<ShipmentAddressEntity, Long> {

    Optional<ShipmentAddressEntity> findByIdAndUserId(Long id, Long userId);
}
