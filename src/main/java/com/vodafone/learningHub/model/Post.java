package com.vodafone.learningHub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Integer postId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    @NotEmpty
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name="rating")
    private int rating;
    @OneToMany(mappedBy = "post")
    private Set<Attachment> attachments;
    @NotEmpty(message = "A post must have at least one tag")
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "post_tag", joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag"))
    private Set<Tag> tags;
    @Column(name = "is_deleted")
    private boolean isDeleted = false;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // The date when the post was deleted
}