package com.example.lab4.Services;

import com.example.lab4.Entities.Client;
import com.example.lab4.Entities.Device;
import com.example.lab4.Entities.Record;
import com.example.lab4.Repositories.ClientRepo;
import com.example.lab4.Repositories.DeviceRepo;
import com.example.lab4.Repositories.RecordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class DeviceService {
    @Autowired
    private DeviceRepo deviceRepository;
    @Autowired
    private RecordRepo recordRepository;
    @Autowired
    private ClientRepo clientRepository;

    public Iterable<Device> getAllDevices() {return deviceRepository.findAll(); }

    public Device getDevicesById(Long id) throws Exception{

        Device Device = deviceRepository.findById(id).orElse(null);
        if(Device == null) {
            throw new Exception("Could not find Device");
        }

        return Device;
    }

    public Device createDevice(Long clientId, Device newDevice) throws Exception{
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) {
            throw new Exception("Could not find client");
        }
        Device device = deviceRepository.findDeviceByClient(client);
        if (device != null){
            throw new Exception("Client already has a device");
        }

        newDevice.setClient(client);

        return deviceRepository.save(newDevice);
    }

    public Device updateDevice(Long id, Device updatedDevice) throws Exception {
        Device oldDevice = deviceRepository.findById(id).orElse(null);
        if(oldDevice == null) {
            throw new Exception("Could not find Device");
        }
        updatedDevice.setId(oldDevice.getId());
        return deviceRepository.save(updatedDevice);
    }

    public void deleteDevice(Long id) throws Exception{
        Device deleteDevice = deviceRepository.findById(id).orElse(null);
        if(deleteDevice == null) {
            throw new Exception("Could not find Device");
        }

        deviceRepository.delete(deleteDevice);
    }

    public Record measure(Long id) throws Exception{
        Device device = deviceRepository.findById(id).orElse(null);
        if (device == null) {
            throw new Exception("Device not found");
        }
        Record record = new Record();

        record.setDevice(device);
        record.setMeasurement((int) ((Math.random() * (device.getUpperBound() - device.getLowerBound())) + device.getLowerBound()));
        record.setDateOfMeasurement(new Date());

        return recordRepository.save(record);
    }

    public List<Record> measurements(Long id) throws Exception {
        Device device = deviceRepository.findById(id).orElse(null);
        if (device == null) {
            throw new Exception("Device not found");
        }

        return recordRepository.findRecordsbyDevice(id);
    }
}