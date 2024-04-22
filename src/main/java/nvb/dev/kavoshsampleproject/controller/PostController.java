package nvb.dev.kavoshsampleproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvb.dev.kavoshsampleproject.dto.PostDto;
import nvb.dev.kavoshsampleproject.entity.Post;
import nvb.dev.kavoshsampleproject.mapper.PostMapper;
import nvb.dev.kavoshsampleproject.service.PostLockService;
import nvb.dev.kavoshsampleproject.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final PostLockService postLockService;
    private final AtomicBoolean isProcessing = new AtomicBoolean(false);

    @PostMapping(path = "/post/save")
    public ResponseEntity<PostDto> savePost(@RequestBody @Valid PostDto postDto) {
        synchronized (postLockService.getLock()) {
            if (!isProcessing.compareAndSet(false, true)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Post post = postMapper.toPost(postDto);
            Post savedPost = postService.savePost(post);
            isProcessing.set(false);
            return new ResponseEntity<>(postMapper.toPostDto(savedPost), HttpStatus.CREATED);
        }
    }

    @GetMapping(path = "/post/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        synchronized (postLockService.getLock()) {
            if (!isProcessing.compareAndSet(false, true)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Optional<Post> foundPost = postService.getPostById(id);
            isProcessing.set(false);
            return foundPost.map(post -> {
                PostDto postDto = postMapper.toPostDto(post);
                return new ResponseEntity<>(postDto, HttpStatus.OK);
            }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
    }

    @GetMapping(path = "/post/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        synchronized (postLockService.getLock()) {
            if (!isProcessing.compareAndSet(false, true)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            List<Post> allPosts = postService.getAllPosts();
            List<PostDto> postDtoList = allPosts.stream().map(postMapper::toPostDto).toList();
            isProcessing.set(false);
            return new ResponseEntity<>(postDtoList, HttpStatus.OK);
        }
    }

}
