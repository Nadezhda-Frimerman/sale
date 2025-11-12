package ru.skypro.homework.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ad;
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

    public CommentServiceImpl(CommentRepository commentRepository, MyUserDetailsManager myUserDetailsManager, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.myUserDetailsManager = myUserDetailsManager;
        this.commentMapper = commentMapper;
    }

//    Получение комментариев объявления
    @Override
    public CommentsDto getAllCommentByAdsId(Integer id) {
        myUserDetailsManager.checkUserAuthenticated();
        List<CommentDto> commentsDtos=commentMapper.CommentListToCommentDtoList(commentRepository.findAllCommentByAdsId(id));
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setCount(commentsDtos.size());
        commentsDto.setResults(commentsDtos);
        return commentsDto;
    }
//    Добавление комментария к объявлению
    @Override
    public CommentDto addComment(Integer id, CreateOrUpdateCommentDto createOrUpdateCommentDto){
    myUserDetailsManager.checkUserAuthenticated();
    Comment comment = commentMapper.CreateOrUpdateCommentDtoToCommentEntity(createOrUpdateCommentDto);
    commentRepository.save(comment);
    return commentMapper.CommentToCommentDto(comment);
}
//    Удаление комментария
    @Override
    public void removeComment (Integer id){
    myUserDetailsManager.checkUserAuthenticated();
    if (!commentRepository.getById(id).getAd().getUser().equals(myUserDetailsManager.getCurrentUser())){
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
    commentRepository.deleteById(id);
}
//    Обновление комментария
    @Override
    public CommentDto updateComment (Integer id, CreateOrUpdateCommentDto createOrUpdateCommentDto){
    myUserDetailsManager.checkUserAuthenticated();
    if (!commentRepository.getById(id).getAd().getUser().equals(myUserDetailsManager.getCurrentUser())){
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
    Comment comment = commentRepository.findById(id).orElseThrow();
    comment.setText(createOrUpdateCommentDto.getText());
    commentRepository.save(comment);
    CommentDto commentDto = commentMapper.CommentToCommentDto(comment);
    return commentDto;
}
}
