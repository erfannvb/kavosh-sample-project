package nvb.dev.kavoshsampleproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nvb.dev.kavoshsampleproject.dto.PostDto;
import nvb.dev.kavoshsampleproject.entity.Post;
import nvb.dev.kavoshsampleproject.mapper.PostMapper;
import nvb.dev.kavoshsampleproject.security.JwtService;
import nvb.dev.kavoshsampleproject.security.impl.UserDetailsServiceImpl;
import nvb.dev.kavoshsampleproject.service.PostLockService;
import nvb.dev.kavoshsampleproject.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

import static nvb.dev.kavoshsampleproject.MotherObject.*;
import static nvb.dev.kavoshsampleproject.constant.Constant.BEARER;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtService jwtService;

    @Mock
    PostMapper postMapper;

    @Mock
    ReentrantLock reentrantLock;

    @MockBean
    PostService postService;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    PostLockService postLockService;

    private String generateToken() {
        UserDetails user = User.builder()
                .username("dummy")
                .password("dummy")
                .roles("USER")
                .build();
        return jwtService.generateToken(user);
    }

    @Test
    void testSavePost_Success() throws Exception {
        when(postService.savePost(any(Post.class))).thenReturn(anyValidPost());
        when(postMapper.toPostDto(anyValidPost())).thenReturn(anyValidPostDto());
        when(postMapper.toPost(anyValidPostDto())).thenReturn(anyValidPost());
        when(postLockService.getLock()).thenReturn(reentrantLock);
        when(postLockService.getLock().tryLock()).thenReturn(true);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(post("/api/v1/post/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(asJsonString(anyValidPostDto()))
                )
                .andExpect(status().isCreated());

        verify(postLockService, atLeastOnce()).getLock();
        verify(postLockService.getLock(), timeout(10000)).tryLock();
        verify(postLockService.getLock(), atLeastOnce()).unlock();
    }

    @Test
    void testSavePost_ThrowsUnauthorizedException() throws Exception {
        when(postService.savePost(any(Post.class))).thenThrow(UsernameNotFoundException.class);
        when(postMapper.toPostDto(anyValidPost())).thenReturn(anyValidPostDto());
        when(postMapper.toPost(anyValidPostDto())).thenReturn(anyValidPost());
        when(postLockService.getLock()).thenReturn(reentrantLock);
        when(postLockService.getLock().tryLock()).thenReturn(true);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(post("/api/v1/post/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(asJsonString(anyValidPostDto()))
                )
                .andExpect(status().isUnauthorized());

        verify(postLockService, atLeastOnce()).getLock();
        verify(postLockService.getLock(), timeout(10000)).tryLock();
        verify(postLockService.getLock(), atLeastOnce()).unlock();
    }

    @Test
    void testSavePost_BadRequest_WhenTitleIsBlank() throws Exception {
        Post post = anyValidPost();
        post.setTitle("");

        PostDto postDto = anyValidPostDto();
        postDto.setTitle("");

        when(postService.savePost(any(Post.class))).thenReturn(post);
        when(postMapper.toPostDto(post)).thenReturn(postDto);
        when(postMapper.toPost(postDto)).thenReturn(post);
        when(postLockService.getLock()).thenReturn(reentrantLock);
        when(postLockService.getLock().tryLock()).thenReturn(true);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(post("/api/v1/post/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(asJsonString(postDto))
                )
                .andExpect(status().isBadRequest());

        verify(postLockService, atLeastOnce()).getLock();
        verify(postLockService.getLock(), never()).tryLock();
        verify(postLockService.getLock(), never()).unlock();
    }

    @Test
    void testSavePost_BadRequest_WhenTitleIsNull() throws Exception {
        Post post = anyValidPost();
        post.setTitle(null);

        PostDto postDto = anyValidPostDto();
        postDto.setTitle(null);

        when(postService.savePost(any(Post.class))).thenReturn(post);
        when(postMapper.toPostDto(post)).thenReturn(postDto);
        when(postMapper.toPost(postDto)).thenReturn(post);
        when(postLockService.getLock()).thenReturn(reentrantLock);
        when(postLockService.getLock().tryLock()).thenReturn(true);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(post("/api/v1/post/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(asJsonString(postDto))
                )
                .andExpect(status().isBadRequest());

        verify(postLockService, atLeastOnce()).getLock();
        verify(postLockService.getLock(), never()).tryLock();
        verify(postLockService.getLock(), never()).unlock();
    }

    @Test
    void testGetPostById_Success() throws Exception {
        when(postService.getPostById(anyLong())).thenReturn(Optional.of(anyValidPost()));
        when(postMapper.toPostDto(anyValidPost())).thenReturn(anyValidPostDto());
        when(postMapper.toPost(anyValidPostDto())).thenReturn(anyValidPost());
        when(postLockService.getLock()).thenReturn(reentrantLock);
        when(postLockService.getLock().tryLock()).thenReturn(true);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/v1/post/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isOk());

        verify(postLockService, atLeastOnce()).getLock();
        verify(postLockService.getLock(), timeout(10000)).tryLock();
        verify(postLockService.getLock(), atLeastOnce()).unlock();
    }

    @Test
    void testGetPostById_ThrowsUnauthorizedException() throws Exception {
        when(postService.getPostById(anyLong())).thenThrow(UsernameNotFoundException.class);
        when(postMapper.toPostDto(anyValidPost())).thenReturn(anyValidPostDto());
        when(postMapper.toPost(anyValidPostDto())).thenReturn(anyValidPost());
        when(postLockService.getLock()).thenReturn(reentrantLock);
        when(postLockService.getLock().tryLock()).thenReturn(true);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/v1/post/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isUnauthorized());

        verify(postLockService, atLeastOnce()).getLock();
        verify(postLockService.getLock(), timeout(10000)).tryLock();
        verify(postLockService.getLock(), atLeastOnce()).unlock();
    }

    @Test
    void testGetPostById_NotFound() throws Exception {
        when(postService.getPostById(anyLong())).thenReturn(Optional.empty());
        when(postMapper.toPostDto(anyValidPost())).thenReturn(anyValidPostDto());
        when(postMapper.toPost(anyValidPostDto())).thenReturn(anyValidPost());
        when(postLockService.getLock()).thenReturn(reentrantLock);
        when(postLockService.getLock().tryLock()).thenReturn(true);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/v1/post/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isNotFound());

        verify(postLockService, atLeastOnce()).getLock();
        verify(postLockService.getLock(), timeout(10000)).tryLock();
        verify(postLockService.getLock(), atLeastOnce()).unlock();
    }

    @Test
    void testGetAllPosts_Success() throws Exception {
        when(postService.getAllPosts()).thenReturn(List.of(anyValidPost()));
        when(postMapper.toPostDto(anyValidPost())).thenReturn(anyValidPostDto());
        when(postMapper.toPost(anyValidPostDto())).thenReturn(anyValidPost());
        when(postLockService.getLock()).thenReturn(reentrantLock);
        when(postLockService.getLock().tryLock()).thenReturn(true);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/v1/post/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isOk());

        verify(postLockService, atLeastOnce()).getLock();
        verify(postLockService.getLock(), timeout(10000)).tryLock();
        verify(postLockService.getLock(), atLeastOnce()).unlock();
    }

    @Test
    void testGetAllPosts_ThrowsUnauthorizedException() throws Exception {
        when(postService.getAllPosts()).thenThrow(UsernameNotFoundException.class);
        when(postMapper.toPostDto(anyValidPost())).thenReturn(anyValidPostDto());
        when(postMapper.toPost(anyValidPostDto())).thenReturn(anyValidPost());
        when(postLockService.getLock()).thenReturn(reentrantLock);
        when(postLockService.getLock().tryLock()).thenReturn(true);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/v1/post/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isUnauthorized());

        verify(postLockService, atLeastOnce()).getLock();
        verify(postLockService.getLock(), timeout(10000)).tryLock();
        verify(postLockService.getLock(), atLeastOnce()).unlock();
    }

    @Test
    void testSavePost_Conflict() throws Exception {
        when(postService.savePost(any(Post.class))).thenReturn(anyValidPost());
        when(postMapper.toPostDto(anyValidPost())).thenReturn(anyValidPostDto());
        when(postMapper.toPost(anyValidPostDto())).thenReturn(anyValidPost());
        when(postLockService.getLock()).thenReturn(reentrantLock);
        when(postLockService.getLock().tryLock()).thenReturn(false);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(post("/api/v1/post/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(asJsonString(anyValidPostDto()))
                )
                .andExpect(status().isConflict());

        verify(postLockService, atLeastOnce()).getLock();
        verify(postLockService.getLock(), timeout(10000)).tryLock();
        verify(postLockService.getLock(), never()).unlock();
    }

    @Test
    void testGetPostById_Conflict() throws Exception {
        when(postService.getPostById(anyLong())).thenReturn(Optional.of(anyValidPost()));
        when(postMapper.toPostDto(anyValidPost())).thenReturn(anyValidPostDto());
        when(postMapper.toPost(anyValidPostDto())).thenReturn(anyValidPost());
        when(postLockService.getLock()).thenReturn(reentrantLock);
        when(postLockService.getLock().tryLock()).thenReturn(false);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/v1/post/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isConflict());

        verify(postLockService, atLeastOnce()).getLock();
        verify(postLockService.getLock(), timeout(10000)).tryLock();
        verify(postLockService.getLock(), never()).unlock();
    }

    @Test
    void testGetAllPosts_Conflict() throws Exception {
        when(postService.getAllPosts()).thenReturn(List.of(anyValidPost()));
        when(postMapper.toPostDto(anyValidPost())).thenReturn(anyValidPostDto());
        when(postMapper.toPost(anyValidPostDto())).thenReturn(anyValidPost());
        when(postLockService.getLock()).thenReturn(reentrantLock);
        when(postLockService.getLock().tryLock()).thenReturn(false);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/v1/post/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isConflict());

        verify(postLockService, atLeastOnce()).getLock();
        verify(postLockService.getLock(), timeout(10000)).tryLock();
        verify(postLockService.getLock(), never()).unlock();
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}