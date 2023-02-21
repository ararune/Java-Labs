package com.example.lab4.Services;

import com.example.lab4.Entities.Client;
import com.example.lab4.Entities.Device;
import com.example.lab4.Entities.Record;
import com.example.lab4.Repositories.ClientRepo;
import com.example.lab4.Repositories.DeviceRepo;
import com.example.lab4.Repositories.RecordRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class DeviceService {

    private final DeviceRepo deviceRepository;

    private final RecordRepo recordRepository;

    private final ClientRepo clientRepository;
    public List<Device> getAllDevicesWithRecords() {
        return deviceRepository.findAllWithRecords();
    }

    public Iterable<Device> getAllDevices() {return deviceRepository.findAll(); }

    public Device getDevicesById(Long id) throws Exception{

        Device Device = deviceRepository.findById(id).orElse(null);
        if(Device == null) {
            throw new Exception("No such device found.");
        }

        return Device;
    }

    public Device createDevice(Long clientId, Device newDevice) throws Exception{
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) {
            throw new Exception("No such client found.");
        }
        Device device = deviceRepository.findDeviceByClient(client);
        if (device != null){
            throw new Exception("The client already has a device");
        }

        newDevice.setClient(client);

        return deviceRepository.save(newDevice);
    }

    public Device updateDevice(Long id, Device updatedDevice) throws Exception {
        Device currentDevice = deviceRepository.findById(id).orElse(null);
        if(currentDevice == null) {
            throw new Exception("No such device found.");
        }
        updatedDevice.setId(currentDevice.getId());
        return deviceRepository.save(updatedDevice);
    }

    public void deleteDevice(Long id) throws Exception{
        Device deleteDevice = deviceRepository.findById(id).orElse(null);
        if(deleteDevice == null) {
            throw new Exception("No such device found.");
        }

        deviceRepository.delete(deleteDevice);
    }

//    public Record measure(Long id) throws Exception{
//        Device device = deviceRepository.findById(id).orElse(null);
//        if (device == null) {
//            throw new Exception("No such device found.");
//        }
//        Record record = new Record();
//
//        record.setDevice(device);
//        record.setMeasurement((int) ((Math.random() * (device.getUpperBoundary() - device.getLowBoundary())) + device.getLowBoundary()));
//        record.setDateOfMeasurement(new Date());
//
//        List<Record> allRecords = recordRepository.findRecordsbyDevice(id);
//        int month = record.getDateOfMeasurement().getMonth();
//        if(allRecords.stream().anyMatch(r -> r.getDateOfMeasurement().getMonth() == month))
//        {
//            throw new Exception("The record was already taken this month.");
//        }
//
//
//        return recordRepository.save(record);
//    }

    public Record measure(Long id) throws Exception{
        Device device = deviceRepository.findById(id).orElse(null);
        if (device == null) {
            throw new Exception("Device not found");
        }
        Record record = new Record();

        record.setDevice(device);
        record.setMeasurement((int) ((Math.random() * (device.getUpperBoundary() - device.getLowBoundary())) + device.getLowBoundary()));
        record.setDateOfMeasurement(new Date());

        List<Record> allRecords = recordRepository.findRecordsbyDevice(id);
        int month = record.getDateOfMeasurement().getMonth();
        if(allRecords.stream().anyMatch(r -> r.getDateOfMeasurement().getMonth() == month))
        {
            throw new Exception("Measurement already taken this month");
        }


        return recordRepository.save(record);
    }

    public List<Record> measurements(Long id) throws Exception {
        Device device = deviceRepository.findById(id).orElse(null);
        if (device == null) {
            throw new Exception("No such device found.");
        }

        return recordRepository.findRecordsbyDevice(id);
    }
    public List<Record> allMeasurements() throws Exception {
        List<Record> records = recordRepository.findAll();
        if (records.isEmpty()) {
            throw new Exception("No records found.");
        }

        return recordRepository.findAll();
    }


    public Record updateRecord(Long deviceId, Record updatedRecord, Long id) throws Exception{
        Record currentRecord = recordRepository.findById(id).orElse(null);
        if (currentRecord == null) {
            throw new Exception("No such record found.");
        }
        List<Record> allRecords = recordRepository.findRecordsbyDevice(deviceId);

        int month = updatedRecord.getDateOfMeasurement().getMonth();

        if(allRecords.stream().anyMatch(r -> r.getDateOfMeasurement().getMonth() == month)) {
            throw new Exception("Measurement already taken this month");
        }
        updatedRecord.setId(id);
        updatedRecord.setDevice(currentRecord.getDevice());

        return recordRepository.save(updatedRecord);
    }

    public int totalByYear(long id, int year) throws Exception{
        List<Record> yearRecords = recordRepository.findRecordsbyDevice(id).stream()
                .filter(r -> r.getDateOfMeasurement().getYear()+1900 == year).toList();

        String message = String.format("No records taken in year %s", year);
        if(yearRecords.isEmpty()) {
            throw new Exception(message);
        }
        return yearRecords.stream().map(Record::getMeasurement).reduce(0, Integer::sum);
    }

    public String totalByMonth(long id, int year) throws Exception{
        List<Record> yearRecords = recordRepository.findRecordsbyDevice(id).stream()
                .filter(r -> r.getDateOfMeasurement().getYear()+1900 == year).toList();
        String message = String.format("No records taken in year %s", year);
        if(yearRecords.isEmpty()) {
            throw new Exception(message);
        }
        return yearRecords.stream().map(r -> r.getDateOfMeasurement().getMonth() + ": " + r.getMeasurement() + " ")
                .collect(Collectors.joining());
    }

    public Record totalByMonthInYear(long id, int year, int month) throws Exception{
        Record monthRecord = recordRepository.findRecordsbyDevice(id).stream()
                .filter(r -> r.getDateOfMeasurement().getYear()+1900 == year)
                .filter(r -> r.getDateOfMeasurement().getMonth() == month).findAny().orElse(null);
        String message = String.format("No records taken in month %s", month);
        if(monthRecord == null) {
            throw new Exception(message);
        }
        return monthRecord;
    }

    public void deleteRecord(Long deviceId, Long id) throws Exception{
        Device device = deviceRepository.findById(deviceId).orElse(null);
        if(device == null) {
            throw new Exception("No such device found.");
        }

        Record recordToDelete = recordRepository.findById(id).orElse(null);
        if(recordToDelete == null) {
            throw new Exception("No such record found.");
        }

        recordRepository.delete(recordToDelete);
    }
}
