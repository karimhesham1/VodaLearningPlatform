package com.vodafone.learningHub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tag")
public class Tag {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name="tag_id")
//    private int tagId;
    @Id
    @Column(name="tag", unique = true)
    private String tag;
//    @ManyToMany(mappedBy = "tags")
//    private Set<Post> posts;
}