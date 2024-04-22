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
import java.util.concurrent.locks.Lock;
import java.util.function.Function;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final PostLockService postLockService;

    @PostMapping(path = "/post/save")
    public ResponseEntity<PostDto> savePost(@RequestBody @Valid PostDto postDto) throws InterruptedException {
        return executeWithLock(locked -> {
            if (Boolean.TRUE.equals(locked)) {
                Post post = postMapper.toPost(postDto);
                Post savedPost = postService.savePost(post);
                return new ResponseEntity<>(postMapper.toPostDto(savedPost), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        });
    }

    @GetMapping(path = "/post/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id) throws InterruptedException {
        return executeWithLock(locked -> {
            if (Boolean.TRUE.equals(locked)) {
                Optional<Post> foundPost = postService.getPostById(id);
                return foundPost.map(post -> {
                    PostDto postDto = postMapper.toPostDto(post);
                    return new ResponseEntity<>(postDto, HttpStatus.OK);
                }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        });
    }

    @GetMapping(path = "/post/all")
    public ResponseEntity<List<PostDto>> getAllPosts() throws InterruptedException {
        return executeListWithLock(locked -> {
            if (Boolean.TRUE.equals(locked)) {
                List<Post> posts = postService.getAllPosts();
                List<PostDto> postDtoList = posts.stream().map(postMapper::toPostDto).toList();
                return new ResponseEntity<>(postDtoList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        });
    }

    private ResponseEntity<PostDto> executeWithLock(Function<Boolean, ResponseEntity<PostDto>> operation) throws InterruptedException {
        Lock lock = postLockService.getLock();
        if (lock.tryLock()) {
            try {
                Thread.sleep(10000);
                return operation.apply(true);
            } finally {
                lock.unlock();
            }
        } else {
            return operation.apply(false);
        }
    }

    private ResponseEntity<List<PostDto>> executeListWithLock(Function<Boolean, ResponseEntity<List<PostDto>>> operation) throws InterruptedException {
        Lock lock = postLockService.getLock();
        if (lock.tryLock()) {
            try {
                Thread.sleep(10000);
                return operation.apply(true);
            } finally {
                lock.unlock();
            }
        } else {
            return operation.apply(false);
        }
    }

}
