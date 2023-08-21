package com.vodafone.learningHub.repository;

import com.vodafone.learningHub.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag findByTag(String tag);

    Boolean existsByTag(String tag);
}
