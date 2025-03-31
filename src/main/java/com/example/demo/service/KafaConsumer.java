package com.example.demo.service;

import com.example.demo.entity.IOTDeviceDetails;
import com.example.demo.enumclass.DeviceStatusDetails;
import com.example.demo.repository.IOTDeviceDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@KafkaListener(topics = "thingwire.devices.responses", groupId = "iot-group")
public class KafaConsumer {
    private final IOTDeviceDetailsRepository iotRepo;

    @KafkaHandler
    public void listen(String message) {
        String[] parts = message.split(":");
        String deviceId = parts[1];
        DeviceStatusDetails status = DeviceStatusDetails.valueOf(parts[2]);

        IOTDeviceDetails device = iotRepo.findById(deviceId).orElse(null);
        if (!Objects.isNull(device)) {
            device.setStatus(status);
            device.setLastSeen(LocalDateTime.now());
            iotRepo.save(device);
        }
    }
}
