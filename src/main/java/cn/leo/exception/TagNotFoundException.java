package cn.leo.exception;

public class TagNotFoundException extends RuntimeException{
    public TagNotFoundException(Integer id){
        super("Could not find tag"+id);
    }
}
