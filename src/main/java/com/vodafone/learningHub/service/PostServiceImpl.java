package com.vodafone.learningHub.service;

import com.vodafone.learningHub.mapper.PostMapper;
import com.vodafone.learningHub.model.Post;
import com.vodafone.learningHub.openapi.model.PostRequest;
import com.vodafone.learningHub.openapi.model.PostResponse;
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
    public PostResponse createPost(PostRequest postRequest) {
        Post post = PostMapper.INSTANCE.postRequestToPost(postRequest);
//        if (post.getTags() == null || post.getTags().isEmpty()) {
//            throw new IllegalArgumentException("A post must have at least one tag");
//        }

        if (post.getTitle() == null || post.getTitle().isEmpty()) {
            throw new IllegalArgumentException("A post must have a title");
        }

        Post postResponse = postRepository.save(post);

        return PostMapper.INSTANCE.postToPostResponse(postResponse);
    }

    @Override
    public PostResponse updatePost(PostRequest postRequest) {

        return null;
    }

    public Post getPostById(int postId) {
        return postRepository.findByPostId(postId);
    }
}
