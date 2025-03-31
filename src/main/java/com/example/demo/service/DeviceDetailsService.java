package com.example.demo.service;

import com.example.demo.entity.IOTDeviceDetails;
import com.example.demo.enumclass.DeviceStatusDetails;
import com.example.demo.repository.IOTDeviceDetailsRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class  DeviceDetailsService {
    @Autowired
    private IOTDeviceDetailsRepository iotRepo;
    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;




    public IOTDeviceDetails getDevice(String id) {
        return iotRepo.findById(id).orElseThrow(() -> new RuntimeException("Device not found"));
    }
    public IOTDeviceDetails updateDevice(String id, IOTDeviceDetails updatedDevice) {
        IOTDeviceDetails obj = getDevice(id);
        obj.setName(updatedDevice.getName());
        obj.setMetadata(null);
        return iotRepo.save(obj);
    }
    public String deleteDevice(String id) {
        iotRepo.deleteById(id);
        return "Device deleted";
    }
    @Transactional
    public IOTDeviceDetails registerDevice(IOTDeviceDetails device) {
        device.setStatus(DeviceStatusDetails.OFFLINE); // Set status before saving
        IOTDeviceDetails savedDevice = iotRepo.save(device); // Save the entity (merges if needed)

        // Publish Kafka event after saving
        kafkaTemplate.send("thingwire.devices.events", "device_registered: " + savedDevice.getId());

        return savedDevice;
    }

}
