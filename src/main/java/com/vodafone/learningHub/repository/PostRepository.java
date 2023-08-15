package com.vodafone.learningHub.repository;

import com.vodafone.learningHub.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
