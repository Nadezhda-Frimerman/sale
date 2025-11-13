package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;

@Service
public interface CommentService {
    CommentsDto getAllCommentByAdsId(Integer id);

    CommentDto addComment(Integer id, CreateOrUpdateCommentDto createOrUpdateCommentDto);

    void removeComment(Integer adId, Integer commentId);

    CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto);
}
