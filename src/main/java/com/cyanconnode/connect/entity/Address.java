package com.cyanconnode.connect.entity;

import jakarta.persistence.*;

@Entity
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "address_line_1",nullable = false)
    private String addressLine1;

    @Column(name = "address_line_2",nullable = false)
    private String addressLine2;

    @Column(name = "city",nullable = false)
    private String city;

    @Column(name = "state",nullable = false)
    private String state;

    @Column(name = "pin_code",nullable = false)
    private int pinCode;
}
