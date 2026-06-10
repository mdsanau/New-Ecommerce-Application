package com.Ecommerce_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String mobile;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private String pincode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
