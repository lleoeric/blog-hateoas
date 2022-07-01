package cn.leo.exception;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(Integer id){
        super("Could not find comment"+id);
    }

    public CommentNotFoundException() {
    }
}
