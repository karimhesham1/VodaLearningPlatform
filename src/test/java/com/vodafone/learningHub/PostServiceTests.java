package com.vodafone.learningHub;

import com.vodafone.learningHub.model.Post;
//import com.vodafone.learningHub.openapi.model.Attachment;
import com.vodafone.learningHub.model.Tag;
import com.vodafone.learningHub.openapi.model.Attachment;
import com.vodafone.learningHub.openapi.model.PostRequest;
import com.vodafone.learningHub.openapi.model.PostResponse;
//import com.vodafone.learningHub.openapi.model.Tag;
import com.vodafone.learningHub.repository.PostRepository;
import com.vodafone.learningHub.service.PostServiceI;
import com.vodafone.learningHub.service.PostServiceImpl;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.naming.ServiceUnavailableException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = LearningHubApplication.class)
class PostServiceTests {
    @Autowired
    private PostServiceI underTest;

    @Test
    void testCreatePost_SuccessfulPostCreation(){
        //Given
        PostRequest postRequest = new PostRequest();

        postRequest.setTitle("Test Title");
        postRequest.setDescription("Test Description");

        //when
        PostResponse postResponse = underTest.createPost(postRequest);


        //then
        assertThat(postResponse.getTitle()).isEqualTo(postRequest.getTitle());
        assertThat(postResponse.getDescription()).isEqualTo(postRequest.getDescription());
        assertThat(postResponse.getPostId()).isNotNull();
        assertThatCode(() -> underTest.createPost(postRequest)).doesNotThrowAnyException();
    }

    /*@Test
    void testCreatePost_SuccessfulPostCreationMultipleTags(){
        //Given
        PostRequest postRequest = new PostRequest();
        com.vodafone.learningHub.openapi.model.Attachment attachment = new Attachment();
        List<String> tags = new ArrayList<>();
        tags.add("Test Tag");
        tags.add("Test Tag 2");

        postRequest.setTitle("Test Title");
        postRequest.setDescription("Test Description");
        postRequest.setTag(tags);
        postRequest.setAttachment(attachment);

        //when
        PostResponse postResponse = underTest.createPost(postRequest);


        //then
        assertThat(postResponse.getTitle()).isEqualTo(postRequest.getTitle());
        assertThat(postResponse.getDescription()).isEqualTo(postRequest.getDescription());
        assertThat(postResponse.getPostId()).isNotNull();
        assertThatCode(() -> underTest.createPost(postRequest)).doesNotThrowAnyException();
    }

    @Test
    void testCreatePost_SuccessfulPostCreationMissingDescription(){
        //Given
        PostRequest postRequest = new PostRequest();
        com.vodafone.learningHub.openapi.model.Attachment attachment = new Attachment();
        List<String> tags = new ArrayList<>();
        tags.add("Test Tag");

        postRequest.setTitle("Test Title");
        postRequest.setTag(tags);
        postRequest.setAttachment(attachment);

        //Mocking
        when(postRepository.save(Mockito.any())).thenReturn(new Post());

        //when
        PostResponse postResponse = underTest.createPost(postRequest);


        //then
        assertThat(postResponse.getTitle()).isEqualTo(postRequest.getTitle());
        assertThat(postResponse.getDescription()).isEqualTo(postRequest.getDescription());
        assertThat(postResponse.getPostId()).isNotNull();
        assertThatCode(() -> underTest.createPost(postRequest)).doesNotThrowAnyException();
    }

    @Test
    void testCreatePost_FailureMissingTitle() {
        // Given
        PostRequest postRequest = new PostRequest();
        Attachment attachment = new Attachment();
        List<String> tags = new ArrayList<>();
        tags.add("Test Tag");

        postRequest.setDescription("Test Description");
        postRequest.setTag(tags);
        postRequest.setAttachment(attachment);

        // When
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            underTest.createPost(postRequest);
        });

        // Then
        String expectedMessage = "A post must have a title";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCreatePost_FailureMissingTags() {
        // Given
        PostRequest postRequest = new PostRequest();
        Attachment attachment = new Attachment();

        postRequest.setTitle("Test Title");
        postRequest.setDescription("Test Description");
        postRequest.setAttachment(attachment);

        // When
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            underTest.createPost(postRequest);
        });

        // Then
        String expectedMessage = "A post must have at least one tag";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCreatePost_FailureNullTag() {
        // Given
        PostRequest postRequest = new PostRequest();
        Attachment attachment = new Attachment();
        List<String> tags = new ArrayList<>();
        tags.add(null);

        postRequest.setTitle("Test Title");
        postRequest.setTag(tags);
        postRequest.setDescription("Test Description");
        postRequest.setAttachment(attachment);

        // When
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            underTest.createPost(postRequest);
        });

        // Then
        String expectedMessage = "A post must have at least one tag";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCreatePost_FailureNullTitle() {
        // Given
        PostRequest postRequest = new PostRequest();
        Attachment attachment = new Attachment();
        List<String> tags = new ArrayList<>();
        tags.add("Test Tag");

        postRequest.setTitle(null);
        postRequest.setTag(tags);
        postRequest.setDescription("Test Description");
        postRequest.setAttachment(attachment);

        // When
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            underTest.createPost(postRequest);
        });

        // Then
        String expectedMessage = "A post must have a title";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCreatePost_FailureServiceUnavailable() {
        // Given
        PostRequest postRequest = new PostRequest();
        Attachment attachment = new Attachment();
        List<String> tags = new ArrayList<>();
        tags.add("Test Tag");

        postRequest.setTitle("Test Title");
        postRequest.setDescription("Test Description");
        postRequest.setTag(tags);
        postRequest.setAttachment(attachment);

        // Mocking behavior of the PostRepository to throw a ServiceUnavailableException
        PostRepository postRepository = Mockito.mock(PostRepository.class);
        when(postRepository.save(Mockito.any())).thenThrow(ServiceUnavailableException.class);

        // When and Then
        assertThatExceptionOfType(ServiceUnavailableException.class)
                .isThrownBy(() -> underTest.createPost(postRequest));
    }*/
}
