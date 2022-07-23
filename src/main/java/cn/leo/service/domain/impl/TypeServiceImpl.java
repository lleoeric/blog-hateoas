package cn.leo.service.domain.impl;

import cn.leo.entities.dao.Type;
import cn.leo.repository.TypeRepository;
import cn.leo.service.domain.TypeService;
import org.springframework.stereotype.Service;

@Service
public class TypeServiceImpl extends BaseServiceImpl<Type, Integer, TypeRepository> implements TypeService {
    public TypeServiceImpl(TypeRepository repository) {
        super(repository);
    }
}
