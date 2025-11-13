package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    /**
     * Comment -> CommentDto
     */
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "authorImage", expression = "java(\"/pictures/\" + comment.getAuthor().getImage().getId())")
//    @Mapping(target = "authorImage", source = "author.image.filePath")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    CommentDto CommentToCommentDto(Comment comment);

    /**
     * CommentDto + User -> Comment
     */
    default Comment CommentDtoToCommentEntity(CommentDto commentDto, User author) {
        if (commentDto == null && author == null) {
            return null;
        }

        Comment comment = new Comment();
        if (commentDto != null) {
            comment.setId(commentDto.getPk());
            comment.setText(commentDto.getText());
            comment.setCreatedAt(commentDto.getCreatedAt());
        }
        comment.setAuthor(author);
        // ad устанавливается отдельно

        return comment;
    }

    /**
     * CreateOrUpdateCommentDto -> Comment
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "ad", ignore = true)
    @Mapping(target = "author", ignore = true)
    Comment CreateOrUpdateCommentDtoToCommentEntity(CreateOrUpdateCommentDto createOrUpdateCommentDto);

    /**
     * Update Comment from CreateOrUpdateCommentDto
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "ad", ignore = true)
    @Mapping(target = "author", ignore = true)
    void updateCommentFromCreateOrUpdateCommentDto(CreateOrUpdateCommentDto createOrUpdateCommentDto, @MappingTarget Comment comment);

    /**
     * List<Comment> -> List<CommentDto>
     */
    List<CommentDto> CommentListToCommentDtoList(List<Comment> comments);

    /**
     * CreateOrUpdateCommentDto + User + Ad -> Comment (комбинированный метод для создания)
     */
    default Comment CreateOrUpdateCommentDtoToCommentEntityWithRelations(CreateOrUpdateCommentDto createOrUpdateCommentDto,
                                                                         User author,
                                                                         ru.skypro.homework.entity.Ad ad,
                                                                         Long createdAt) {
        if (createOrUpdateCommentDto == null) {
            return null;
        }

        return Comment.builder()
                .text(createOrUpdateCommentDto.getText())
                .createdAt(createdAt)
                .author(author)
                .ad(ad)
                .build();
    }
}