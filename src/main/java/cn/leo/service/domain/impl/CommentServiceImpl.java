package cn.leo.service.domain.impl;

import cn.leo.entities.dao.Comment;
import cn.leo.repository.CommentRepository;
import cn.leo.service.domain.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment, Integer, CommentRepository> implements CommentService {
    public CommentServiceImpl(CommentRepository repository) {
        super(repository);
    }


    @Override
    public Comment saveChildComment(Integer id, Comment newChildComment) {
        return null;
    }
}
