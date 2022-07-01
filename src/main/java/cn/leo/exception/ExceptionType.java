package cn.leo.exception;

public enum ExceptionType {
    BLOG_NOTFOUND_EXCEPTION(201, new BlogNotFoundException()),
    COMMENT_NOTFOUND_EXCEPTION(202, new CommentNotFoundException());

    ExceptionType(int i, RuntimeException e) {

    }
}
