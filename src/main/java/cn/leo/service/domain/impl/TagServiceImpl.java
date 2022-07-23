package cn.leo.service.domain.impl;

import cn.leo.entities.dao.Tag;
import cn.leo.repository.TagRepository;
import cn.leo.service.domain.TagService;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl extends BaseServiceImpl<Tag, Integer, TagRepository> implements TagService {
    public TagServiceImpl(TagRepository repository) {
        super(repository);
    }
}
