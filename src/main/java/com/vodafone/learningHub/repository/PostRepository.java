package com.vodafone.learningHub.repository;

import com.vodafone.learningHub.model.Post;
import com.vodafone.learningHub.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findByPostId(Integer postId);
    Boolean existsByPostId(int postId);
    @Query("SELECT p FROM Post p JOIN p.tags t WHERE t.tag IN :tags AND p.title LIKE %:title%")
    List<Post> findByTitleAndTags(String title, Set<String> tags);
    List<Post> findByTitle(String title);
    @Query("SELECT p FROM Post p JOIN p.tags t WHERE t.tag IN :tags")
    List<Post> findByTags(Set<String> tags);


    List<Post> findByIsDeletedIsTrue();
}
