package com.example.lab4.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "device")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lower_boundary")
    private int lowBoundary;
    @Column(name = "upper_boundary")
    private int upperBoundary;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "device")
    private Set<Record> records;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", unique = true)
    private Client client;

}
