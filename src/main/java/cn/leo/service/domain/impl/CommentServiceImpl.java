package cn.leo.service.domain.impl;

import cn.leo.entities.Comment;
import cn.leo.service.domain.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Override
    public Comment saveChildComment(Integer id, Comment newChildComment) {
        return null;
    }
}
