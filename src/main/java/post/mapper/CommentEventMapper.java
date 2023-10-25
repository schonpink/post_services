package post.mapper;

import org.mapstruct.*;
import org.springframework.stereotype.Component;
import post.dto.CommentEventDto;
import post.model.Comment;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentEventMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", source = "commentId")
    @Mapping(target = "post", source = "postId", qualifiedByName = "mapPostIdToPost")
    @Mapping(target = "createdAt", source = "date")
    Comment toCommentEntity(CommentEventDto commentEventDto, @Context long postId);

    @Mapping(target = "commentId", source = "id")
    @Mapping(target = "postId", source = "post.id")
    @Mapping(target = "date", source = "createdAt")
    CommentEventDto toCommentDto(Comment comment);

    @Named("mapPostIdToPost")
    default Post mapPostIdToPost(Long postId) {
        return Post.builder().id(postId).build();
    }
}