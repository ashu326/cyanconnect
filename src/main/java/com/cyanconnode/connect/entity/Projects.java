package com.cyanconnode.connect.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "projects")
public class Projects
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "project_name",nullable = false)
    private String projectName;

    @ManyToOne
    @JoinColumn(name = "address_id",nullable = false)
    private Address siteAddress;
}
