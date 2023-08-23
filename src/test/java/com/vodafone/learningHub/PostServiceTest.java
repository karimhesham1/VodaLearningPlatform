package com.vodafone.learningHub;

import com.vodafone.learningHub.mapper.PostMapper;
import com.vodafone.learningHub.model.Post;
import com.vodafone.learningHub.openapi.model.PostRequest;
import com.vodafone.learningHub.openapi.model.PostResponse;
import com.vodafone.learningHub.openapi.model.Tag;
import com.vodafone.learningHub.service.PostServiceI;
import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import javax.naming.ServiceUnavailableException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest(classes = {LearningHubApplication.class})
class PostServiceTest {

    @Autowired
    private PostServiceI underTest;

    @Nested
    class TestCreatePost {
        @Test
        void testCreatePost_SuccessfulPostCreation() {
            //Given
            PostRequest postRequest = new PostRequest();

            List<Tag> tags = new ArrayList<>();
            Tag newTag = new Tag();
            newTag.setTagName("Test Tag");
            tags.add(newTag);
            postRequest.setTag(tags);
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

        @Test
        void testCreatePost_SuccessfulPostCreationMultipleTags() {
            //Given
            PostRequest postRequest = new PostRequest();

            List<Tag> tags = new ArrayList<>();
            Tag newTag = new Tag();
            newTag.setTagName("Test Tag");
            tags.add(newTag);
            Tag newTag2 = new Tag();
            newTag2.setTagName("Test Tag2");
            tags.add(newTag2);
            postRequest.setTag(tags);

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

        @Test
        void testCreatePost_SuccessfulPostCreationMissingDescription() {
            //Given
            PostRequest postRequest = new PostRequest();
            List<Tag> tags = new ArrayList<>();
            Tag newTag = new Tag();
            newTag.setTagName("Test Tag");
            tags.add(newTag);

            postRequest.setTitle("Test Title");
            postRequest.setTag(tags);

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
            List<Tag> tags = new ArrayList<>();
            Tag newTag = new Tag();
            newTag.setTagName("Test Tag");
            tags.add(newTag);

            postRequest.setDescription("Test Description");
            postRequest.setTag(tags);

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
            //Attachment attachment = new Attachment();

            postRequest.setTitle("Test Title");
            postRequest.setDescription("Test Description");
            //postRequest.setAttachment(attachment);


            // When
            NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> {
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
            List<Tag> tags = new ArrayList<>();
    //        tags.add(null);

            postRequest.setTitle("Test Title");
            postRequest.setDescription("Test Description");
            postRequest.setTag(tags);


            // When
            NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> {
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

            postRequest.setTitle(null);
            postRequest.setDescription("Test Description");
            List<Tag> tags = new ArrayList<>();
            Tag newTag = new Tag();
            newTag.setTagName("Test Tag");
            tags.add(newTag);
            postRequest.setTag(tags);

            // When
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
                underTest.createPost(postRequest);
            });

            // Then
            String expectedMessage = "A post must have a title";
            String actualMessage = exception.getMessage();
            Assertions.assertTrue(actualMessage.contains(expectedMessage));
        }
    }

    //scenarios

//    Successful Post Edit (HTTP 202 Accepted)
//    Attempt to Edit Nonexistent Post (HTTP 404 Not Found)
//    Missing Required Fields (HTTP 400 Bad Request)
//    Successful Post Edit with No Changes (HTTP 202 Accepted)

    @Nested
    class TestUpdatePost {
        PostResponse initialPostResponse;
        @BeforeEach
        void setUp(){
            PostRequest initialPostRequest = new PostRequest();
            initialPostRequest.setTitle("Initial Title");
            initialPostRequest.setDescription("Initial Description");

            List<Tag> tags = new ArrayList<>();
            Tag newTag = new Tag();
            newTag.setTagName("Test Tag");
            tags.add(newTag);
            initialPostRequest.setTag(tags);

            initialPostResponse  = underTest.createPost(initialPostRequest);

        }

        @Test
        void testUpdatePost_SuccessfulPostEdit() throws NotFoundException {


            Integer postId = initialPostResponse.getPostId();

            PostRequest updatedPostRequest = new PostRequest();
            updatedPostRequest.setTitle("Updated Title");
            updatedPostRequest.setDescription("Updated Description");


            List<Tag> tags = new ArrayList<>();
            Tag newTag = new Tag();
            newTag.setTagName("updated Tag");
            tags.add(newTag);
            updatedPostRequest.setTag(tags);


            // When
            PostResponse updatedPostResponse = underTest.updatePost(postId, updatedPostRequest);

            // Then
            assertThat(updatedPostResponse.getPostId()).isEqualTo(postId);
            assertThat(updatedPostResponse.getTitle()).isEqualTo(updatedPostRequest.getTitle());
            assertThat(updatedPostResponse.getDescription()).isEqualTo(updatedPostRequest.getDescription());
            assertThat(updatedPostResponse.getTag()).isEqualTo(updatedPostRequest.getTag());
        }

        @Test
        void testUpdatePost_NonexistentPost() {
            // Given
            int nonexistentPostId = 999999;
            PostRequest updatedPostRequest = new PostRequest();
            updatedPostRequest.setTitle("Updated Title");
            updatedPostRequest.setDescription("Updated Description");

            List<Tag> tags = new ArrayList<>();
            Tag newTag = new Tag();
            newTag.setTagName("updated Tag");
            tags.add(newTag);
            updatedPostRequest.setTag(tags);


            // When
            // Attempt to update a nonexistent post
            NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
                underTest.updatePost(nonexistentPostId, updatedPostRequest);
            });

            // Then
            assertThat(exception.getClass()).isEqualTo(NotFoundException.class);
        }

        @Test
        void testUpdatePost_MissingFields() {
            // Given
            PostRequest initialPostRequest = new PostRequest();
            initialPostRequest.setTitle("Initial Title");
            initialPostRequest.setDescription("Initial Description");
            List<Tag> tags = new ArrayList<>();
            Tag newTag = new Tag();
            newTag.setTagName("Test Tag");
            tags.add(newTag);
            initialPostRequest.setTag(tags);

            PostResponse initialPostResponse = underTest.createPost(initialPostRequest);

            int postId = initialPostResponse.getPostId();

            PostRequest updatedPostRequest = new PostRequest();
            updatedPostRequest.setTitle(null);
            updatedPostRequest.setDescription("new description");

            // When
            // Attempt to update with missing fields
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
                underTest.updatePost(postId, updatedPostRequest);
            });

            // Then
            String expectedErrorMessage = "A post must have a title"; // Adjust to the actual error message
            String actualErrorMessage = exception.getMessage();
            Assertions.assertTrue(actualErrorMessage.contains(expectedErrorMessage));
        }

        @Test
        void testUpdatePost_NoChanges() throws NotFoundException {
            // Given
            PostRequest initialPostRequest = new PostRequest();
            initialPostRequest.setTitle("Initial Title");
            initialPostRequest.setDescription("Initial Description");
            List<Tag> tags = new ArrayList<>();
            Tag newTag = new Tag();
            newTag.setTagName("Test Tag");
            tags.add(newTag);
            initialPostRequest.setTag(tags);

            PostResponse initialPostResponse = underTest.createPost(initialPostRequest);

            int postId = initialPostResponse.getPostId();

            // When
            // Attempt to update with no changes
            PostResponse updatedPostResponse = underTest.updatePost(postId, initialPostRequest);

            // Then
            assertThat(updatedPostResponse).isEqualTo(initialPostResponse);
        }
    }
}
