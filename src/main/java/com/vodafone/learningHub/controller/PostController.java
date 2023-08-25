package com.vodafone.learningHub.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.vodafone.learningHub.model.Post;
import com.vodafone.learningHub.openapi.PostApi;
import com.vodafone.learningHub.openapi.model.InlineResponse202;
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
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class PostController implements PostApi {

    private final PostServiceI postServiceI;


    @Override
    public ResponseEntity uploadPost(PostRequest postRequest) {
        try {
            PostResponse postResponse  = postServiceI.createPost(postRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);

        } catch(IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
//            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, ex.getMessage());

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
//            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @Override
    public ResponseEntity updatePost(Integer postId, PostRequest postRequest) {
        try {
            PostResponse postResponse = postServiceI.updatePost(postId, postRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);

        } catch (IllegalArgumentException ex) {
//            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());

        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
    @Override
    public ResponseEntity getPost (Map<String, String> params) {
        ResponseEntity responseEntity = postServiceI.getPost(params);

        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }

    @Override
    public ResponseEntity deletePost(Integer postId) {
        try {
            postServiceI.deletePost(postId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new InlineResponse202().message("Deleted Successfully"));

        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new InlineResponse202().message(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new InlineResponse202().message(ex.getMessage()));
        }
    }
}
