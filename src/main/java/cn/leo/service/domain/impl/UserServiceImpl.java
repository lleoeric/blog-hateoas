package cn.leo.service.domain.impl;

import cn.leo.entities.dao.User;
import cn.leo.entities.vo.UserLoginVo;
import cn.leo.repository.UserRepository;
import cn.leo.service.domain.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Integer, UserRepository> implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Optional<User> findByUsernameAndPassword(UserLoginVo user) {
        return repository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
}
