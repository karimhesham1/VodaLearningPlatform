package com.vodafone.learningHub.service;

import com.vodafone.learningHub.mapper.PostMapper;
import com.vodafone.learningHub.model.Post;
import com.vodafone.learningHub.model.Tag;
import com.vodafone.learningHub.openapi.model.PostRequest;
import com.vodafone.learningHub.openapi.model.PostResponse;
import com.vodafone.learningHub.repository.PostRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
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
//        post.setTags(tags);
        if (post.getTitle() == null || post.getTitle().isEmpty()) {
            throw new IllegalArgumentException("A post must have a title");
        }

        Post postResponse = postRepository.save(post);

        return PostMapper.INSTANCE.postToPostResponse(postResponse);
    }

    @Override
    public PostResponse updatePost(Integer postId,PostRequest postRequest) throws NotFoundException {
        Post post=getPostById(postId);
        if (!existsByPostId(postId)) {
            throw new NotFoundException("Post not found");
        }

        Post editedPost = PostMapper.INSTANCE.postRequestToPost(postRequest);

        if (editedPost.getTags() == null || editedPost.getTags().isEmpty()) {
            throw new IllegalArgumentException("A post must have at least one tag");
        }

        post.setTitle(editedPost.getTitle());
        post.setDescription(editedPost.getDescription());
        post.setTags(editedPost.getTags());
        post.setAttachments(editedPost.getAttachments());

        Post postResponse= postRepository.save(post);

        return PostMapper.INSTANCE.postToPostResponse(postResponse);
    }

//@Override
//public PostResponse updatePost(Integer postId,PostRequest postRequest) {
//    Post post=getPostById(postId);
////    Post editedPost = PostMapper.INSTANCE.postRequestToPost(postRequest);
//
//    if (postRequest.getTags() == null || postRequest.getTags().isEmpty()) {
//        throw new IllegalArgumentException("A post must have at least one tag");
//    }
//
//    post.setTitle(postRequest.getTitle());
//    post.setDescription(postRequest.getDescription());
//    post.setTags(postRequest.getTags());
//    post.setAttachments(postRequest.getAttachments());
//
//
//
//
//    Post postResponse= postRepository.save(post);
//
//    return PostMapper.INSTANCE.postToPostResponse(postResponse);
//}
//
//    private Set<Tag> tagListToSet(List<com.vodafone.learningHub.openapi.model.Tag> tags) {
//        if (tags == null || tags.isEmpty()) {
//            return new HashSet<>();
//        }
//        Set<Tag> tagSet = new HashSet<>();
//        for (com.vodafone.learningHub.openapi.model.Tag tag : tags) {
//            Tag newTag = new Tag();
//            newTag.setTag(tag.getTagName());
//            tagSet.add(newTag);
//        }
//        return tagSet;
//    }

    public Post getPostById(int postId) {
        return postRepository.findByPostId(postId);
    }

    public Boolean existsByPostId(int postId) {
        return postRepository.existsByPostId(postId);
    }
}
