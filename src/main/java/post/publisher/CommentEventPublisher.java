package post.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import post.dto.CommentEventDto;

@Component
public class CommentEventPublisher extends AbstractPublisher<CommentEventDto>{
    public CommentEventPublisher(RedisTemplate<String, Object> redisTemplate,
                                 ObjectMapper jsonMapper,
                                 @Value("${spring.data.redis.channels.comment_channels}")String channel) {
        super(redisTemplate, jsonMapper, channel);
    }
}