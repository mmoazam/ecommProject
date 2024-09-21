package com.shopper.ecomm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    private String address1;

    private  String address2;

    @NotBlank
    private String city;

    @NotBlank
    @Size(min = 6, max = 7)
    private String postCode;

    @NotBlank
    private String country;

    @ToString.Exclude
    @OneToMany(mappedBy = "addresses")
    private List<EcommUser> ecommUsers = new ArrayList<>();

    public Address(String address1, String address2, String city, String postCode, String country) {
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.postCode = postCode;
        this.country = country;
    }
}
