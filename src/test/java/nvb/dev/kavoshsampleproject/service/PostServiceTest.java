package nvb.dev.kavoshsampleproject.service;

import nvb.dev.kavoshsampleproject.entity.Post;
import nvb.dev.kavoshsampleproject.exception.PostNotFoundException;
import nvb.dev.kavoshsampleproject.repository.PostRepository;
import nvb.dev.kavoshsampleproject.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static nvb.dev.kavoshsampleproject.MotherObject.anyValidPost;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostServiceImpl postService;

    @Test
    void testThatSavePostCanCreateNewPost() {
        when(postRepository.save(any(Post.class))).thenReturn(anyValidPost());

        Post savedPost = postService.savePost(anyValidPost());

        assertEquals("dummy", savedPost.getTitle());
        verify(postRepository, atLeastOnce()).save(any(Post.class));
    }

    @Test
    void testThatGetPostByIdReturnsTheExistingPost() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(anyValidPost()));

        Post post = postService.getPostById(123L).orElseThrow();

        assertEquals(123L, post.getId());
        assertEquals("dummy", post.getTitle());
        verify(postRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void testThatGetPostByIdDoeNotReturnThePostIfPostDoesNotExist() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        PostNotFoundException postNotFoundException = assertThrows(PostNotFoundException.class, () -> postService.getPostById(1L));
        assertEquals("Post with id '1' does not exist", postNotFoundException.getMessage());
        verify(postRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void testThatGetAllPostsReturnsListOfPosts() {
        when(postRepository.findAll()).thenReturn(List.of(anyValidPost(), anyValidPost()));

        List<Post> postList = postService.getAllPosts();

        assertEquals("dummy", postList.getFirst().getTitle());
        assertEquals("dummy", postList.getLast().getTitle());
        verify(postRepository, atLeastOnce()).findAll();
    }

    @Test
    void testThatGetAllPostsReturnsEmptyListIfNoPostFound() {
        when(postRepository.findAll()).thenReturn(Collections.emptyList());

        PostNotFoundException postNotFoundException = assertThrows(PostNotFoundException.class, () -> postService.getAllPosts());
        assertEquals("There is no posts.", postNotFoundException.getMessage());
        verify(postRepository, atLeastOnce()).findAll();
    }

}