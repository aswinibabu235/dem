package com.example.demo.controller;

import com.example.demo.entity.IOTDeviceDetails;
import com.example.demo.service.DeviceDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceDetailsController {
    @Autowired
    private DeviceDetailsService deviceDetailsService;

    @PostMapping
    public ResponseEntity<IOTDeviceDetails> registerDevice(@RequestBody IOTDeviceDetails device) {
        return ResponseEntity.ok(deviceDetailsService.registerDevice(device));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IOTDeviceDetails> getDevice(@PathVariable String id) {
        return ResponseEntity.ok(deviceDetailsService.getDevice(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IOTDeviceDetails> updateDevice(@PathVariable String id, @RequestBody IOTDeviceDetails device) {
        System.out.println("Received PUT request for ID: " + id);
        System.out.println("Request Body: " + device);
        return ResponseEntity.ok(deviceDetailsService.updateDevice(id, device));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable String id) {
        return ResponseEntity.ok(deviceDetailsService.deleteDevice(id));
    }
}
