package com.example.lab4.Controllers;

import com.example.lab4.Entities.Device;
import com.example.lab4.Entities.Record;
import com.example.lab4.Repositories.DeviceRepo;
import com.example.lab4.Repositories.RecordRepo;
import com.example.lab4.Services.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/devices")
public class DeviceController {

    public final DeviceService deviceService;
    private final RecordRepo recordRepository;
    private final DeviceRepo deviceRepository;

    @PostMapping("/records")
    public String measureCurrentUsage(@RequestParam Long deviceId, @RequestParam int lowBoundary, @RequestParam int upperBoundary, RedirectAttributes redirectAttributes) {

        Device device = deviceRepository.findById(deviceId).orElse(null);
        if (device == null) {
            redirectAttributes.addFlashAttribute("message", "Device not found");
            return "forward:/records";
        }
        device.setLowBoundary(lowBoundary);
        device.setUpperBoundary(upperBoundary);
        deviceRepository.save(device);
        Record record = new Record();
        record.setDevice(device);
        record.setMeasurement((int) ((Math.random() * (upperBoundary - lowBoundary)) + lowBoundary));
        record.setDateOfMeasurement(new Date());
        List<Record> allRecords = recordRepository.findRecordsbyDevice(deviceId);
        int month = record.getDateOfMeasurement().getMonth();
        if(allRecords.stream().anyMatch(r -> r.getDateOfMeasurement().getMonth() == month))
        {
            redirectAttributes.addFlashAttribute("message", "Measurement already taken this month");
            return "forward:/records";
        }
        recordRepository.save(record);
        redirectAttributes.addFlashAttribute("message", "Measurement taken successfully");
        return "forward:/records";
    }

    @GetMapping("/all")
    public String getAllDevicesWithRecords(Model model) {
        String url = "http://localhost:8080/devices";
        RestTemplate restTemplate = new RestTemplate();
        Device[] devices = restTemplate.getForObject(url, Device[].class);
//        List<Device> devices = deviceService.getAllDevicesWithRecords();
        model.addAttribute("devices", devices);
        return "records";
    }

    @GetMapping("/records")
    public String getAllRecords(Model model) throws Exception {
        List<Record> records = recordRepository.findAll();
        model.addAttribute("records", records);
        return "records";
    }
    @GetMapping(value = "/")
    public Iterable<Device> getAll() {
        return deviceService.getAllDevices();
    }

    @PostMapping("/{clientId}")
    public ResponseEntity<Device> createDevice(@PathVariable Long clientId, @RequestBody Device newDevice) {
        ResponseEntity<Device> response;
        try {
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
    public ResponseEntity<Device> updateClient(@PathVariable Long id, @RequestBody Device updatedDevice) {
        ResponseEntity<Device> response;
        try {
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body(deviceService.updateDevice(id, updatedDevice));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        try {
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
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping(path = "/measurements/{id}")
    public String getMeasurementById(@PathVariable("id") Long id, Model model) throws Exception {
        List<Record> record = deviceService.measurements(id);
        model.addAttribute("record", record);
        return "record";
    }
    @PutMapping("/{deviceId}/measurements/{id}")
    public ResponseEntity<Record> updateRecord(@PathVariable Long deviceId, @RequestBody Record updatedRecord, @PathVariable Long id) {
        ResponseEntity<Record> response;
        try {
            response = ResponseEntity.status(HttpStatus.OK).body(deviceService.updateRecord(deviceId, updatedRecord, id));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return response;
    }

    @GetMapping("/{id}/measurements/{year}")
    public ResponseEntity<String> totalByYear(@PathVariable Long id, @PathVariable int year) {
        ResponseEntity<String> response;
        try {
            response = ResponseEntity.status(HttpStatus.OK).body(year + " :" + deviceService.totalByYear(id, year));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return response;
    }

    @GetMapping("/{id}/measurements/{year}/monthly")
    public ResponseEntity<String> totalByMonth(@PathVariable Long id, @PathVariable int year) {
        ResponseEntity<String> response;
        try {
            response = ResponseEntity.status(HttpStatus.OK).body(year + ": " + deviceService.totalByMonth(id, year));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return response;
    }

    @GetMapping("/{id}/measurements/{year}/{month}")
    public ResponseEntity<String> totalByMonthInYear(@PathVariable Long id, @PathVariable int year, @PathVariable int month) {
        ResponseEntity<String> response;
        try {
            response = ResponseEntity.status(HttpStatus.OK).body(month + " :" + deviceService.totalByMonthInYear(id, year, month).getMeasurement());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return response;
    }

    @DeleteMapping("/{deviceId}/measurements/{id}")
    public ResponseEntity<String> deleteRecord(@PathVariable Long deviceId, @PathVariable Long id) {
        try {
            deviceService.deleteRecord(deviceId, id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted Record");

    }

}