package com.example.lab4.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="record")
public class Record {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="date_of_measurement", nullable = false)
    private Date dateOfMeasurement;

    @Column(name="measurement", nullable = false)
    private int measurement;


    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "device_id")
    private Device device;

}
