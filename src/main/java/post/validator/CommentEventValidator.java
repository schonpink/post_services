package post.validator;


import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import post.dto.CommentEventDto;
import post.exception.EntityNotFoundException;
import post.model.Comment;
import post.client.UserServiceClient;
import post.repository.CommentRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Validated
public class CommentEventValidator {
    private final UserServiceClient userServiceClient;
    private final CommentRepository commentRepository;

    public void validateBeforeCreate(@Valid CommentEventDto commentEventDto) {
        if (!userExists(commentEventDto.getAuthorId())) {
            throw new EntityNotFoundException("Author with Id " + commentEventDto.getAuthorId() + " does not exist");
        }

        if (!postExists(commentEventDto.getPostId())) {
            throw new EntityNotFoundException("Post with Id " + commentEventDto.getPostId() + " does not exist");
        }

        if (!commentExists(commentEventDto.getCommentId())) {
            throw new ValidationException("Comment with Id " + commentEventDto.getCommentId() + " does not exist");
        }
    }

    private boolean userExists(long userId) {
        try {
            userServiceClient.getUser(userId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean postExists(long postId) {
        return commentRepository.findAllByPostId(postId).isEmpty();

    }

    private boolean commentExists(long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        return comment.isPresent();
    }
}