package com.example.lab4.Repositories;

import com.example.lab4.Entities.Client;
import com.example.lab4.Entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface DeviceRepo extends JpaRepository<Device, Long> {
    Device findDeviceByClient(Client client);
}
