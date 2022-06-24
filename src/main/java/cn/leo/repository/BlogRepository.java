package cn.leo.repository;

import cn.leo.entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

}