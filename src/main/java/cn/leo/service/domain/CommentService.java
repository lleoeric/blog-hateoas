package cn.leo.service.domain;

import cn.leo.entities.Comment;

public interface CommentService {
    Comment saveChildComment(Integer id, Comment newChildComment);
}
