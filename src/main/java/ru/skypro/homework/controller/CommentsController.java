package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.impl.CommentsServiceImpl;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentsServiceImpl commentsService;

    public CommentsController(CommentsServiceImpl commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping("/{id}/comments")
    public Comments getComments(@PathVariable(name = "id") Integer id) {
        return new Comments();
    }

    @PostMapping("/{id}/comments")
    public Comment addComment(@PathVariable(name = "id") Integer id,
                              @RequestBody CreateOrUpdateComment createOrUpdateComment) {
        return new Comment();
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public void deleteComment(@PathVariable(name = "adId") Integer adId,
                              @PathVariable(name = "commentId") Integer commentId) {
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public Comment updateComment(@PathVariable(name = "adId") Integer adId,
                                 @PathVariable(name = "commentId") Integer commentId,
                                 @RequestBody CreateOrUpdateComment createOrUpdateComment) {
        return new Comment();
    }
}
