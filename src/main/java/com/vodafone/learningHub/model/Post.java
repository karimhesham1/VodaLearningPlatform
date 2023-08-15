package com.vodafone.learningHub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int post_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name="rating")
    private int rating;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "attachment_id", referencedColumnName = "attachment_id")
    private Attachment attachment;


}
