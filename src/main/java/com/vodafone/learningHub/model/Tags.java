package com.vodafone.learningHub.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="tags")
public class Tags {
    @Column(name="tag_id")
    private int tagId;

    @Column(name="tag")
    private String tag;

}
