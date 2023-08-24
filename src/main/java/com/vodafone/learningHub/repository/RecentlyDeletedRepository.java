package com.vodafone.learningHub.repository;

import com.vodafone.learningHub.model.RecentlyDeletedPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RecentlyDeletedRepository extends JpaRepository<RecentlyDeletedPost, Integer> {
    List<RecentlyDeletedPost> findAllByDeletedAtBefore(LocalDateTime thresholdDate);
}
