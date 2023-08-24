package com.vodafone.learningHub.service;

import com.vodafone.learningHub.mapper.PostMapper;
import com.vodafone.learningHub.model.Post;
import com.vodafone.learningHub.openapi.model.PostRequest;
import com.vodafone.learningHub.openapi.model.PostResponse;
import com.vodafone.learningHub.openapi.model.Tag;
import com.vodafone.learningHub.repository.PostRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostServiceI{

    private final PostRepository postRepository;
    private final TagServiceImp tagServiceImp;

    @Override
    public PostResponse createPost(PostRequest postRequest) {
        Post post = PostMapper.INSTANCE.postRequestToPost(postRequest);
        if (post.getTags() == null || post.getTags().isEmpty()) {
            throw new NullPointerException("A post must have at least one tag");
        }

        for(Tag tag:postRequest.getTag()){
            if(tag.getTagName() == null){
                throw new NullPointerException("Tag cannot be null");
            }
            if(!tagServiceImp.existsById(tag.getTagName())){
                com.vodafone.learningHub.model.Tag newTag = new com.vodafone.learningHub.model.Tag();
                newTag.setTag(tag.getTagName());
                tagServiceImp.saveTag(newTag);
            }
        }

        if (post.getTitle() == null || post.getTitle().isEmpty()) {
            throw new IllegalArgumentException("A post must have a title");
        }

        Post postResponse = postRepository.save(post);

        return PostMapper.INSTANCE.postToPostResponse(postResponse);
    }

    @Transactional
    @Override
    public PostResponse updatePost(Integer postId,PostRequest postRequest) throws NotFoundException {
        Post post = getPostById(postId);
        if (!existsByPostId(postId)) {
            throw new NotFoundException("Post not found");
        }

        Post editedPost = PostMapper.INSTANCE.postRequestToPost(postRequest);

        if(editedPost.getTitle() == null) {
            throw new IllegalArgumentException("A post must have a title");
        }

        if (editedPost.getTags() == null || editedPost.getTags().isEmpty()) {
            throw new IllegalArgumentException("A post must have at least one tag");
        }

        post.setTitle(editedPost.getTitle());
        post.setDescription(editedPost.getDescription());
        post.setTags(editedPost.getTags());

        post.setAttachments(editedPost.getAttachments() != null?editedPost.getAttachments():post.getAttachments());

        Post postResponse= postRepository.save(post);

        return PostMapper.INSTANCE.postToPostResponse(postResponse);
    }


    @Override
    @Transactional
    public void deletePost(Integer postId) throws NotFoundException {
        if( (!existsByPostId(postId)) || ( getPostById(postId).isDeleted() ) ){
            throw new NotFoundException("Post not found");
        }
        Post post = getPostById(postId);
        post.setDeleted(true);
        post.setDeletedAt(LocalDateTime.now());
        postRepository.save(post);
    }


    public Post getPostById(Integer postId) {
        return postRepository.findByPostId(postId);
    }

    public Boolean existsByPostId(int postId) {
        return postRepository.existsByPostId(postId);
    }
}
