package com.vodafone.learningHub.service;

import com.vodafone.learningHub.model.Post;
import com.vodafone.learningHub.openapi.model.PostRequest;
import com.vodafone.learningHub.openapi.model.PostResponse;

public interface PostServiceI {

    public PostResponse createPost(PostRequest postRequest);
    public PostResponse updatePost(PostRequest postRequest);

    public Post getPostById(int postId);
    
}
