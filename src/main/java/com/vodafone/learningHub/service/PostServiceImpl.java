package com.vodafone.learningHub.service;

import com.vodafone.learningHub.mapper.PostMapper;
import com.vodafone.learningHub.model.Post;
import com.vodafone.learningHub.model.Tag;
import com.vodafone.learningHub.openapi.model.PostRequest;
import com.vodafone.learningHub.openapi.model.PostResponse;
import com.vodafone.learningHub.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostServiceI{

    private final PostRepository postRepository;
    private final TagServiceImp tagServiceImp;

    @Override
    public PostResponse createPost(PostRequest postRequest) {
        Post post = PostMapper.INSTANCE.postRequestToPost(postRequest);
        if (post.getTags() == null || post.getTags().isEmpty()) {
            throw new IllegalArgumentException("A post must have at least one tag");
        }
//        tagServiceImp.deleteAllTags();
//        Set<Tag> tags = new HashSet<>();
//        for(Tag tag : post.getTags()) {
////            if (!tagServiceImp.existsByTag(tag.getTag())) {
//////                Tag newTag = tagServiceImp.saveTag(tag);
//////                tags.add(newTag);
////            }else {
//////                tags.add(tagServiceImp.findTagByTag(tag.getTag()));
////            }
//            Tag newTag = tagServiceImp.findTagByTag(tag.getTag());
//        }

        if (post.getTitle() == null || post.getTitle().isEmpty()) {
            throw new IllegalArgumentException("A post must have a title");
        }

        Post postResponse = postRepository.save(post);

        return PostMapper.INSTANCE.postToPostResponse(postResponse);
    }

    @Override
    public PostResponse updatePost(Integer postId,PostRequest postRequest) {

        return null;
    }

    public Post getPostById(int postId) {
        return postRepository.findByPostId(postId);
    }
}
