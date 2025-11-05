package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.entity.Ad;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {

    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    @Test
    void testCommentToCommentDto() {
        // Given
        User author = User.builder()
                .id(1)
                .firstName("John")
                .image("avatar.jpg")
                .build();

        Comment comment = Comment.builder()
                .id(100)
                .text("This is a test comment")
                .createdAt(1672531200000L) // 01.01.2023
                .author(author)
                .build();

        // When
        CommentDto commentDto = commentMapper.CommentToCommentDto(comment);

        // Then
        assertNotNull(commentDto);
        assertEquals(100, commentDto.getPk());
        assertEquals(1, commentDto.getAuthor());
        assertEquals("avatar.jpg", commentDto.getAuthorImage());
        assertEquals("John", commentDto.getAuthorFirstName());
        assertEquals(1672531200000L, commentDto.getCreatedAt());
        assertEquals("This is a test comment", commentDto.getText());
    }

    @Test
    void testCommentDtoToCommentEntity() {
        // Given
        CommentDto commentDto = CommentDto.builder()
                .pk(100)
                .text("Test comment text")
                .createdAt(1672531200000L)
                .author(1)
                .build();

        User author = User.builder()
                .id(1)
                .firstName("John")
                .build();

        // When
        Comment comment = commentMapper.CommentDtoToCommentEntity(commentDto, author);

        // Then
        assertNotNull(comment);
        assertEquals(100, comment.getId());
        assertEquals("Test comment text", comment.getText());
        assertEquals(1672531200000L, comment.getCreatedAt());
        assertEquals(author, comment.getAuthor());
        assertNull(comment.getAd()); // ad устанавливается отдельно
    }

    @Test
    void testCreateOrUpdateCommentDtoToCommentEntity() {
        // Given
        CreateOrUpdateCommentDto createDto = CreateOrUpdateCommentDto.builder()
                .text("New comment text")
                .build();

        // When
        Comment comment = commentMapper.CreateOrUpdateCommentDtoToCommentEntity(createDto);

        // Then
        assertNotNull(comment);
        assertEquals("New comment text", comment.getText());
        // Игнорируемые поля
        assertNull(comment.getId());
        assertNull(comment.getCreatedAt());
        assertNull(comment.getAd());
        assertNull(comment.getAuthor());
    }

    @Test
    void testUpdateCommentFromCreateOrUpdateCommentDto() {
        // Given
        Comment existingComment = Comment.builder()
                .id(100)
                .text("Old text")
                .createdAt(1672531200000L)
                .build();

        CreateOrUpdateCommentDto updateDto = CreateOrUpdateCommentDto.builder()
                .text("Updated text")
                .build();

        // When
        commentMapper.updateCommentFromCreateOrUpdateCommentDto(updateDto, existingComment);

        // Then
        assertEquals("Updated text", existingComment.getText());
        // Игнорируемые поля не должны измениться
        assertEquals(100, existingComment.getId());
        assertEquals(1672531200000L, existingComment.getCreatedAt());
    }

    @Test
    void testCreateOrUpdateCommentDtoToCommentEntityWithRelations() {
        // Given
        CreateOrUpdateCommentDto createDto = CreateOrUpdateCommentDto.builder()
                .text("Comment with relations")
                .build();

        User author = User.builder()
                .id(1)
                .firstName("John")
                .build();

        Ad ad = Ad.builder()
                .id(50)
                .title("Test Ad")
                .build();

        Long createdAt = 1672531200000L;

        // When
        Comment comment = commentMapper.CreateOrUpdateCommentDtoToCommentEntityWithRelations(
                createDto, author, ad, createdAt);

        // Then
        assertNotNull(comment);
        assertEquals("Comment with relations", comment.getText());
        assertEquals(createdAt, comment.getCreatedAt());
        assertEquals(author, comment.getAuthor());
        assertEquals(ad, comment.getAd());
        assertNull(comment.getId()); // ID генерируется БД
    }

    @Test
    void testCommentListToCommentDtoList() {
        // Given
        User author = User.builder().id(1).firstName("John").image("avatar.jpg").build();

        Comment comment1 = Comment.builder()
                .id(1)
                .text("First comment")
                .createdAt(1672531200000L)
                .author(author)
                .build();

        Comment comment2 = Comment.builder()
                .id(2)
                .text("Second comment")
                .createdAt(1672617600000L)
                .author(author)
                .build();

        List<Comment> comments = List.of(comment1, comment2);

        // When
        List<CommentDto> commentDtos = commentMapper.CommentListToCommentDtoList(comments);

        // Then
        assertNotNull(commentDtos);
        assertEquals(2, commentDtos.size());

        CommentDto dto1 = commentDtos.get(0);
        assertEquals(1, dto1.getPk());
        assertEquals("First comment", dto1.getText());
        assertEquals("John", dto1.getAuthorFirstName());

        CommentDto dto2 = commentDtos.get(1);
        assertEquals(2, dto2.getPk());
        assertEquals("Second comment", dto2.getText());
        assertEquals("John", dto2.getAuthorFirstName());
    }

    @Test
    void testNullSafety() {
        // Проверяем обработку null
        assertDoesNotThrow(() -> {
            commentMapper.CommentToCommentDto(null);
            commentMapper.CreateOrUpdateCommentDtoToCommentEntity(null);
            commentMapper.CommentListToCommentDtoList(null);

            Comment comment = new Comment();
            commentMapper.updateCommentFromCreateOrUpdateCommentDto(null, comment);
        });
    }

    @Test
    void testCommentToCommentDto_WithNullAuthor() {
        // Given
        Comment comment = Comment.builder()
                .id(100)
                .text("Comment without author")
                .createdAt(1672531200000L)
                .author(null)
                .build();

        // When
        CommentDto commentDto = commentMapper.CommentToCommentDto(comment);

        // Then
        assertNotNull(commentDto);
        assertEquals(100, commentDto.getPk());
        assertEquals("Comment without author", commentDto.getText());
        assertNull(commentDto.getAuthor());
        assertNull(commentDto.getAuthorImage());
        assertNull(commentDto.getAuthorFirstName());
    }

    @Test
    void testCommentDtoToCommentEntity_WithNullAuthor() {
        // Given
        CommentDto commentDto = CommentDto.builder()
                .pk(100)
                .text("Test comment")
                .build();

        // When
        Comment comment = commentMapper.CommentDtoToCommentEntity(commentDto, null);

        // Then
        assertNotNull(comment);
        assertEquals(100, comment.getId());
        assertEquals("Test comment", comment.getText());
        assertNull(comment.getAuthor());
    }

    @Test
    void testCreateOrUpdateCommentDtoToCommentEntityWithRelations_WithNullDto() {
        // When
        Comment comment = commentMapper.CreateOrUpdateCommentDtoToCommentEntityWithRelations(
                null, new User(), new Ad(), 1672531200000L);

        // Then
        assertNull(comment);
    }
}