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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final PostLockService postLockService;

    @PostMapping(path = "/post/save")
    public ResponseEntity<?> savePost(@RequestBody @Valid PostDto postDto) throws InterruptedException {
        return executeWithLock(() -> {
            Post post = postMapper.toPost(postDto);
            Post savedPost = postService.savePost(post);
            return new ResponseEntity<>(postMapper.toPostDto(savedPost), HttpStatus.CREATED);
        });
    }

    @GetMapping(path = "/post/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) throws InterruptedException {
        return executeWithLock(() -> {
            Optional<Post> foundPost = postService.getPostById(id);
            return foundPost.map(post -> {
                PostDto postDto = postMapper.toPostDto(post);
                return new ResponseEntity<>(postDto, HttpStatus.OK);
            }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        });
    }

    @GetMapping(path = "/post/all")
    public ResponseEntity<?> getAllPosts() throws InterruptedException {
        return executeWithLock(() -> {
            List<Post> postList = postService.getAllPosts();
            List<PostDto> postDtoList = postList.stream().map(postMapper::toPostDto).toList();
            return ResponseEntity.ok().body(postDtoList);
        });
    }

    private ResponseEntity<?> executeWithLock(Supplier<ResponseEntity<?>> operation) throws InterruptedException {
        Lock lock = postLockService.getLock();
        if (lock.tryLock()) {
            try {
                Thread.sleep(10000);
                return operation.get();
            } finally {
                lock.unlock();
            }
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Another action is in progress. Please try again later.");
        }
    }

}
