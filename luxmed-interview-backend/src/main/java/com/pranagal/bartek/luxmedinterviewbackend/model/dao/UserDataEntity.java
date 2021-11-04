package com.pranagal.bartek.luxmedinterviewbackend.model.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class UserDataEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "personal_id")
    private String personalId;

    @OneToMany(mappedBy = "user")
    private List<ShipmentAddressEntity> shipmentAddresses;

}
