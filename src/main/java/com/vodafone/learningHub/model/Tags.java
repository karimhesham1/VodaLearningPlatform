package com.vodafone.learningHub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="tags")
public class Tags {
    @Column(name="tag_id")
    private int tagId;

    @Column(name="tag")
    private String tag;

}
