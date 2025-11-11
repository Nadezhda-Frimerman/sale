package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.service.impl.CommentServiceImpl;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@Tag(name = "Комментарии", description = "Методы для работы с комментариями")
public class CommentController {
    private final CommentServiceImpl commentService;

    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}/comments")
    @Operation(operationId = "getComments",
            summary = "Получение комментариев объявления",
            tags = {"Комментарии"})
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Not found")
    public CommentsDto getComments(@PathVariable(name = "id") Integer id) {
        return new CommentsDto();
    }

    @PostMapping("/{id}/comments")
    @PreAuthorize("hasRole('USER')")
    @Operation(operationId = "addComment",
            summary = "Добавление комментария к объявлению",
            tags = {"Комментарии"})
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Not found")
    public CommentDto addComment(@PathVariable(name = "id") Integer id,
                                 @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        return new CommentDto();
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or @commentService.getCommentById(#commentId).author.email == authentication.name")
    @Operation(operationId = "deleteComment",
            summary = "Удаление комментария",
            tags = {"Комментарии"})
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public void deleteComment(@PathVariable(name = "adId") Integer adId,
                              @PathVariable(name = "commentId") Integer commentId) {
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    @PreAuthorize("@commentService.getCommentById(#commentId).author.email == authentication.name")
    @Operation(operationId = "updateComment",
            summary = "Обновление комментария",
            tags = {"Комментарии"})
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Not found")
    public CommentDto updateComment(@PathVariable(name = "adId") Integer adId,
                                    @PathVariable(name = "commentId") Integer commentId,
                                    @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        return new CommentDto();
    }
}
