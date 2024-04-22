package nvb.dev.kavoshsampleproject.service;

import nvb.dev.kavoshsampleproject.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Post savePost(Post post);

    Optional<Post> getPostById(long id);

    List<Post> getAllPosts();

}
