package com.vodafone.learningHub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="region")
public class Region {
    @Id
    @Column(name="region_id")
    private int regionId;
    @Column(name="region")
    private String region;
}
