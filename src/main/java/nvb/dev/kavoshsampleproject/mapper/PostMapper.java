package nvb.dev.kavoshsampleproject.mapper;

import nvb.dev.kavoshsampleproject.dto.PostDto;
import nvb.dev.kavoshsampleproject.entity.Post;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMapper {

    @Mapping(source = "post.id", target = "id")
    @Mapping(source = "post.title", target = "title")
    PostDto toPostDto(Post post);

    @Mapping(source = "postDto.id", target = "id")
    @Mapping(source = "postDto.title", target = "title")
    Post toPost(PostDto postDto);

}
