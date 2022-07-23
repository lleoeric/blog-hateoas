package cn.leo.service.domain.impl;

import cn.leo.entities.dao.Blog;
import cn.leo.entities.dao.Tag;
import cn.leo.repository.BlogRepository;
import cn.leo.service.domain.BlogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl extends BaseServiceImpl<Blog, Integer, BlogRepository> implements BlogService {
    public final BlogRepository repository;
    public BlogServiceImpl(BlogRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<Tag> findTagsById(Integer id) {

        return repository.findTagsById(id);
    }
}
