package nvb.dev.kavoshsampleproject.service.impl;

import lombok.RequiredArgsConstructor;
import nvb.dev.kavoshsampleproject.entity.Post;
import nvb.dev.kavoshsampleproject.exception.PostNotFoundException;
import nvb.dev.kavoshsampleproject.repository.PostRepository;
import nvb.dev.kavoshsampleproject.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> getPostById(long id) {
        return Optional.ofNullable(postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id)));
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        if (postList.isEmpty()) throw new PostNotFoundException();
        return postList;
    }
}
