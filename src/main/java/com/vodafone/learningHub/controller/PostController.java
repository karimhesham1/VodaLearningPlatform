package com.vodafone.learningHub.controller;

import com.vodafone.learningHub.model.Post;
import com.vodafone.learningHub.openapi.api.PostApi;
import com.vodafone.learningHub.openapi.model.PostRequest;
import com.vodafone.learningHub.openapi.model.PostResponse;
import com.vodafone.learningHub.service.PostServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
public class PostController implements PostApi  {

    private final PostServiceI postServiceI;


    @Override
    public ResponseEntity<PostResponse> uploadPost(PostRequest postRequest) {
        try {
            PostResponse post  = postServiceI.createPost(postRequest);
//            ResponseEntity.crea
        } catch(IndexOutOfBoundsException | IllegalArgumentException ex) {

        } catch (RuntimeException ex) {

        }

        return null;
    }


}
