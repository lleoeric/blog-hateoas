package cn.leo.service.domain;

import cn.leo.entities.dao.User;
import cn.leo.entities.vo.UserLoginVo;

import java.util.Optional;

public interface UserService {


    Optional<User> findByUsernameAndPassword(UserLoginVo user);
}
