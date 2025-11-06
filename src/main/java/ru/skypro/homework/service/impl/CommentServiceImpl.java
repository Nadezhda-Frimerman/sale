package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;

import javax.persistence.EntityNotFoundException;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment getCommentById(Integer commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
    }
}
