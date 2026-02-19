package com.cyanconnode.connect.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dlms_keys")
public class Keys
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "project_id")
    private Projects projectId;

    @Column(name = "key_name", columnDefinition = "VARBINARY(512)")
    private byte[] keyName;
}
