package cn.leo.service.domain;

import cn.leo.entities.dao.Tag;

import java.util.List;

public interface BlogService{
    List<Tag> findTagsById(Integer id);
}
