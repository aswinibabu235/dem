package com.example.demo.entity;

import com.example.demo.enumclass.DeviceStatusDetails;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
@Table(name = "iotdevices")
@Data
public class IOTDeviceDetails {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private DeviceStatusDetails status;

    private LocalDateTime lastSeen;

    @Column(columnDefinition = "JSON")
    private String metadata;
    @Version
    private Integer version;

}
