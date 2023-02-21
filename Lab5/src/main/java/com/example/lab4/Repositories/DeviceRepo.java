package com.example.lab4.Repositories;

import com.example.lab4.Entities.Client;
import com.example.lab4.Entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepo extends JpaRepository<Device, Long> {
    Device findDeviceByClient(Client client);

    @Query("SELECT d FROM Device d JOIN FETCH d.records JOIN FETCH d.client")
    List<Device> findAllWithRecords();
}
