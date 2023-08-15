package com.vodafone.learningHub.service;

import com.vodafone.learningHub.model.Post;
import com.vodafone.learningHub.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostServiceI{

    private final PostRepository postRepository;

    @Override
    public Post createPost(Post post) {

        return null;
    }
}
