package com.example.lab4.Controllers;

import com.example.lab4.Entities.Device;
import com.example.lab4.Entities.Record;
import com.example.lab4.Services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    public DeviceService deviceService;

    @GetMapping()
    public Iterable<Device> getAll() { return deviceService.getAllDevices(); }

    @PostMapping("/{clientId}")
    public ResponseEntity createDevice(@PathVariable Long clientId, @RequestBody Device newDevice) {
        ResponseEntity response;
        try{
            response = ResponseEntity.status(HttpStatus.CREATED).body(deviceService.createDevice(clientId, newDevice));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return response;
    }

    @GetMapping("/{id}")
    public Device getById(@PathVariable Long id) {
        Device device;
        try {
            device = deviceService.getDevicesById(id);
        } catch (Exception ex) {
            return null;
        }
        return device;
    }

    @PutMapping("/{id}")
    public ResponseEntity updateClient(@PathVariable Long id, @RequestBody Device updatedDevice) {
        ResponseEntity response;
        try {
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body(deviceService.updateDevice(id, updatedDevice));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        try{
            deviceService.deleteDevice(id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted Device");
    }

    @GetMapping("/{id}/measure")
    public Record measureCurrentUsage(@PathVariable Long id) {
        try {
            return deviceService.measure(id);
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/{id}/measurements")
    public List<Record> getMeasurements(@PathVariable Long id) {
        try {
            return deviceService.measurements(id);
        } catch (Exception ex){
            return null;
        }
    }
}
