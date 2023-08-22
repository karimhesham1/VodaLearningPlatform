package com.vodafone.learningHub.service;

import com.vodafone.learningHub.model.Tag;
import com.vodafone.learningHub.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImp {
    private final TagRepository tagRepository;

//    public Tag findTagByTag(String tag) {
//        return tagRepository.findByTag(tag);
//    }
//
//    public Boolean existsByTag(String tag) {
//        return tagRepository.existsByTag(tag);
//    }
//
//    public Tag saveTag(Tag tag) {
//        return tagRepository.save(tag);
//    }
//
//    public void deleteAllTags() {
//        tagRepository.deleteAll();
//    }

}
