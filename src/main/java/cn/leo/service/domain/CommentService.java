package cn.leo.service.domain;

import cn.leo.entities.dao.Comment;

public interface CommentService {
    Comment saveChildComment(Integer id, Comment newChildComment);
}
