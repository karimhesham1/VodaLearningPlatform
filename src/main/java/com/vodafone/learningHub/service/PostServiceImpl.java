package com.vodafone.learningHub.service;

import com.vodafone.learningHub.model.Post;
import com.vodafone.learningHub.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostServiceI{

    private final PostRepository postRepository;

    @Override
    public Post createPost(Post post) {
        if (post.getTags() == null || post.getTags().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A post must have at least one tag");
        }

        if (post.getTitle() == null || post.getTitle().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A post must have a title");
        }

        try{
            postRepository.save(post);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save post");
        }

        return null;
    }
}
