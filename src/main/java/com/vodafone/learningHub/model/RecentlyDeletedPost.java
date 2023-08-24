package com.vodafone.learningHub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("DELETED")
public class RecentlyDeletedPost extends Post {
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // The date when the post was deleted
}
