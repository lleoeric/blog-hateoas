package cn.leo.repository;

import cn.leo.entities.dao.Blog;
import cn.leo.entities.dao.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    @Query("select b.tags from Blog b where b.id = ?1")
    List<Tag> findTagsById(Integer id);

}