package com.cyanconnode.connect.entity;

import jakarta.persistence.*;

@Entity
public class Meters
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "meter_id")
    private String meterId;

    @Column(name = "meter_serial_no")
    private String meterSerialNO;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Projects projectId;

    @Column(name = "dlmskey_id")
    private String dlmskeyId;
}
