package com.vodafone.learningHub.service;

import com.vodafone.learningHub.mapper.PostMapper;
import com.vodafone.learningHub.model.Post;
import com.vodafone.learningHub.model.RecentlyDeletedPost;
import com.vodafone.learningHub.openapi.model.PostRequest;
import com.vodafone.learningHub.openapi.model.PostResponse;
import com.vodafone.learningHub.openapi.model.Tag;
import com.vodafone.learningHub.repository.PostRepository;
import com.vodafone.learningHub.repository.RecentlyDeletedRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostServiceI{

    private final PostRepository postRepository;
    private final TagServiceImp tagServiceImp;
    private final RecentlyDeletedRepository recentlyDeletedRepository;

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

    public void deletePost(Integer postId){
        if(!existsByPostId(postId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        Post post = getPostById(postId);
        postRepository.delete(post);

        RecentlyDeletedPost recentlyDeletedPost = new RecentlyDeletedPost();
        recentlyDeletedPost.setPostId(postId);
        recentlyDeletedPost.setTitle(post.getTitle());
        recentlyDeletedPost.setDescription(post.getDescription());
        recentlyDeletedPost.setTags(post.getTags());
        recentlyDeletedPost.setAttachments(post.getAttachments());
        recentlyDeletedPost.setDeletedAt(java.time.LocalDateTime.now());
        recentlyDeletedRepository.save(recentlyDeletedPost);
    }

    @Scheduled(cron = "0 0 0 * *")
    public void deleteOldRecentlyDeletedPosts(){
        LocalDateTime threshold = LocalDateTime.now().minusDays(30);
        List<RecentlyDeletedPost> recentlyDeletedPosts = recentlyDeletedRepository.findAllByDeletedAtBefore(threshold);
        recentlyDeletedRepository.deleteAll(recentlyDeletedPosts);
    }

    public Post getPostById(Integer postId) {
        return postRepository.findByPostId(postId);
    }

    public Boolean existsByPostId(int postId) {
        return postRepository.existsByPostId(postId);
    }
}
