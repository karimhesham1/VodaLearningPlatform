package com.vodafone.learningHub.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.vodafone.learningHub.model.Post;
import com.vodafone.learningHub.openapi.api.PostApi;
import com.vodafone.learningHub.openapi.model.PostRequest;
import com.vodafone.learningHub.openapi.model.PostResponse;
import com.vodafone.learningHub.service.PostServiceI;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.net.URI;


@RequiredArgsConstructor
@RestController
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

    @Override
    public ResponseEntity<PostResponse> updatePost(Integer postId, PostRequest postRequest) {
        try {
            PostResponse postResponse = postServiceI.updatePost(postId, postRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);

        } catch (IllegalArgumentException ex) {
//            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, ex.getMessage());
            return ResponseEntity.badRequest().build();

        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }



}
