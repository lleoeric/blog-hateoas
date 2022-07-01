package cn.leo.repository;

import cn.leo.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    /**
     * 通过blogId查询comment
     * @param id
     * @return
     */
    List<Comment> findByBlog_Id(Integer id);


}