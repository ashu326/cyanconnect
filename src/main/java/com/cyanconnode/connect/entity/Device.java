package com.cyanconnode.connect.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Device
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "device_id")
    private Long deviceId;

    @Column(name = "state")
    private String state;

    @Column(name = "supply_status")
    private int supplyStatus;

    @Column(name = "device_alarm_profile_id")
    private Long deviceAlarmProfileId;

    @Column(name = "device_node_id")
    private Long deviceNodeId;

    @Column(name = "device_profile_set_id")
    private Long deviceProfileSetId;

    @Column(name = "device_type")
    private Long deviceType;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "altitude")
    private int altitude;

    @OneToOne
    @JoinColumn(name = "project_id")
    private Projects projectId;

    @OneToOne
    @JoinColumn(name = "dlms_key_id")
    private Keys dlmsKeyId;
}
