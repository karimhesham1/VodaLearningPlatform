package com.vodafone.learningHub.service;

import com.vodafone.learningHub.model.Post;
import com.vodafone.learningHub.openapi.model.PostRequest;
import com.vodafone.learningHub.openapi.model.PostResponse;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PostServiceI {

    public PostResponse createPost(PostRequest postRequest);
    public PostResponse updatePost(Integer postId,PostRequest postRequest) throws NotFoundException;
    public ResponseEntity getPost(Map<String, String> params);

    public Post getPostById(Integer postId);
    
}
