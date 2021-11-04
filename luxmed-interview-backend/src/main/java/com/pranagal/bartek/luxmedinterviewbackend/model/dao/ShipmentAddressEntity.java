package com.pranagal.bartek.luxmedinterviewbackend.model.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "shipment_address")
@Data
public class ShipmentAddressEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="email")
    private String email;
    @Column(name = "address")
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserDataEntity user;

}
