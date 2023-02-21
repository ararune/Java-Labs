package com.example.lab4.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;

    @Column(name = "street")
    private String street;
    @JsonIgnore
    @OneToOne(mappedBy = "address")
    private Client client;

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", county='" + country + '\'' +
                ", streetNumber=" + street +
                '}';
    }

}
