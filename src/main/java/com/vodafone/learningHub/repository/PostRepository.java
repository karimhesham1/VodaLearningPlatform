package com.vodafone.learningHub.repository;

import com.vodafone.learningHub.model.Post;
//import com.vodafone.learningHub.model.RecentlyDeletedPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findByPostId(Integer postId);
    Boolean existsByPostId(int postId);
}
