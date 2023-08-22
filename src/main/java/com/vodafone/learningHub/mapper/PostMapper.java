package com.vodafone.learningHub.mapper;

import com.vodafone.learningHub.model.Post;
import com.vodafone.learningHub.model.Tag;
import com.vodafone.learningHub.openapi.model.PostRequest;
import com.vodafone.learningHub.openapi.model.PostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);
    @Mapping(source = "tags", target = "tag", qualifiedByName = "tagSetToList")
    PostResponse postToPostResponse(Post post);
    @Mapping(source = "tag", target = "tags", qualifiedByName = "tagListToSet")
    Post postRequestToPost(PostRequest postRequest);

    @Named("tagSetToList")
    default List<com.vodafone.learningHub.openapi.model.Tag> tagSetToList(Set<Tag> tags) {
        if (tags == null || tags.isEmpty()) {
            return new ArrayList<>();
        }
//        return tags.stream().map(tag -> new Tag().setTagName(tag.getName())).collect(Collectors.toList());
        List<com.vodafone.learningHub.openapi.model.Tag> tagList = new ArrayList<>();
        for (Tag tag : tags) {
            tagList.add(new com.vodafone.learningHub.openapi.model.Tag().tagName(tag.getTag()));
        }
        return tagList;
    }

    @Named("tagListToSet")
    default Set<Tag> tagListToSet(List<com.vodafone.learningHub.openapi.model.Tag> tags) {
        if (tags == null || tags.isEmpty()) {
            return new HashSet<>();
        }
        Set<Tag> tagSet = new HashSet<>();
        for (com.vodafone.learningHub.openapi.model.Tag tag : tags) {
            Tag newTag = new Tag();
            newTag.setTag(tag.getTagName());
            tagSet.add(newTag);
        }
        return tagSet;
    }

//    @Named("tagListToSet")
//    default Set<Tag> tagListToSet(List<com.vodafone.learningHub.openapi.model.Tag> tags) {
//        Set<Tag> tagSet = new HashSet<>();
//        for (com.vodafone.learningHub.openapi.model.Tag tag : tags) {
//            tag.setTagName(tag.getTag());
//        }
//        return tagSet;
//    }

}
