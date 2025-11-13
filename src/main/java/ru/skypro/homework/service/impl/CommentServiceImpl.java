package ru.skypro.homework.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.MyUserDetailsManager;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final MyUserDetailsManager myUserDetailsManager;
    private final CommentMapper commentMapper;
    private final AdServiceImpl adServiceImpl;

    public CommentServiceImpl(CommentRepository commentRepository, MyUserDetailsManager myUserDetailsManager, CommentMapper commentMapper, AdServiceImpl adServiceImpl) {
        this.commentRepository = commentRepository;
        this.myUserDetailsManager = myUserDetailsManager;
        this.commentMapper = commentMapper;
        this.adServiceImpl = adServiceImpl;
    }

    //    Получение комментариев объявления
    @Override
    public CommentsDto getAllCommentByAdsId(Integer id) {
        myUserDetailsManager.checkUserAuthenticated();
        List<CommentDto> thisAdCommentsDto = commentMapper.CommentListToCommentDtoList(commentRepository.findAllCommentByAdsId(id));
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setCount(thisAdCommentsDto.size());
        commentsDto.setResults(thisAdCommentsDto);
        return commentsDto;
    }

    //    Добавление комментария к объявлению
    @Override
    public CommentDto addComment(Integer id, CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        myUserDetailsManager.checkUserAuthenticated();
        Comment comment = commentMapper.CreateOrUpdateCommentDtoToCommentEntity(createOrUpdateCommentDto);
        comment.setCreatedAt(System.currentTimeMillis());
        comment.setAd(adServiceImpl.findAdById(id));
        comment.setAuthor(myUserDetailsManager.getCurrentUser());
        commentRepository.save(comment);
        return commentMapper.CommentToCommentDto(comment);
    }

    //    Удаление комментария
    @Override
    public void removeComment(Integer adId, Integer commentId) {
        myUserDetailsManager.checkUserAuthenticated();
        if (!findComment(commentId).getAd().getUser().equals(myUserDetailsManager.getCurrentUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        commentRepository.deleteById(commentId);
    }

    //    Обновление комментария
    @Override
    public CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        myUserDetailsManager.checkUserAuthenticated();
        if (!findComment(commentId).getAd().getUser().equals(myUserDetailsManager.getCurrentUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.setText(createOrUpdateCommentDto.getText());
        commentRepository.save(comment);
        return commentMapper.CommentToCommentDto(comment);
    }

    public Comment findComment(Integer id) {
        return commentRepository.findById(id).orElseThrow();
    }
}
