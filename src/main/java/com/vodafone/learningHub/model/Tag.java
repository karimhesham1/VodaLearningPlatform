package com.vodafone.learningHub.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="tag_id")
    private int tagId;
    @Column(name="tag")
    private String tag;
    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts;
}