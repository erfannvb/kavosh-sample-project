package nvb.dev.kavoshsampleproject.mapper;

import nvb.dev.kavoshsampleproject.dto.PostDto;
import nvb.dev.kavoshsampleproject.entity.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static nvb.dev.kavoshsampleproject.MotherObject.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {PostMapperImpl.class})
class PostMapperTest {

    @Autowired
    PostMapper postMapper;

    @Test
    void testToPostDto() {
        PostDto postDto = postMapper.toPostDto(anyValidPost());
        assertEquals(anyValidPost().getId(), postDto.getId());
        assertEquals(anyValidPost().getTitle(), postDto.getTitle());
    }

    @Test
    void testToPostDtoNullFields() {
        PostDto postDto = postMapper.toPostDto(anyInvalidPost());
        assertNull(postDto.getId());
        assertNull(postDto.getTitle());
    }

    @Test
    void testToPostDtoNull() {
        PostDto postDto = postMapper.toPostDto(null);
        assertNull(postDto);
    }

    @Test
    void testToPost() {
        Post post = postMapper.toPost(anyValidPostDto());
        assertEquals(anyValidPostDto().getId(), post.getId());
        assertEquals(anyValidPostDto().getTitle(), post.getTitle());
    }

    @Test
    void testToPostNullFields() {
        Post post = postMapper.toPost(anyInvalidPostDto());
        assertNull(post.getId());
        assertNull(post.getTitle());
    }

    @Test
    void testToPostNull() {
        Post post = postMapper.toPost(null);
        assertNull(post);
    }

}