package com.vodafone.learningHub.controller;

import com.vodafone.learningHub.model.Post;
import com.vodafone.learningHub.openapi.api.PostApi;
import com.vodafone.learningHub.openapi.model.PostRequest;
import com.vodafone.learningHub.openapi.model.PostResponse;
import com.vodafone.learningHub.service.PostServiceI;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;


@RequiredArgsConstructor
public class PostController implements PostApi  {

    private final PostServiceI postServiceI;


    @Override
    public ResponseEntity<PostResponse> uploadPost(PostRequest postRequest) {
        try {
            PostResponse postResponse  = postServiceI.createPost(postRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);

        } catch(IllegalArgumentException ex) {
//            return ResponseEntity.badRequest().body(ex.getMessage());
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }


    }


}
