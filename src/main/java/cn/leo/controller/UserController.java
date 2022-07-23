package cn.leo.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.leo.entities.dao.User;
import cn.leo.entities.vo.UserLoginVo;
import cn.leo.service.domain.impl.UserServiceImpl;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserServiceImpl service;

    UserController(UserServiceImpl userService) {
        this.service = userService;
    }

    @GetMapping
    public EntityModel<?> link() {

        return EntityModel.of(linkTo(methodOn(UserController.class).link()).withSelfRel(),
                linkTo(methodOn(UserController.class).login(new UserLoginVo())).withRel("login"),
                linkTo(methodOn(UserController.class).isLogin()).withRel("isLogin"),
                linkTo(methodOn(UserController.class).logout()).withRel("logout"));
    }

    @PostMapping("login")
    public EntityModel<?> login(@RequestBody UserLoginVo userLoginVo) {
        //检查是否登录
        User userEntity = service.findByUsernameAndPassword(userLoginVo).orElseThrow(RuntimeException::new);
        StpUtil.login(userEntity.getId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return EntityModel.of(tokenInfo);
    }

    @GetMapping("isLogin")
    public EntityModel<?> isLogin() {
        System.out.println(StpUtil.isLogin());
        int id = StpUtil.getLoginIdAsInt();
        User user = service.findById(id).orElseThrow(RuntimeException::new);
        System.out.println(id);

        return EntityModel.of(user);
    }

    @GetMapping("logout")
    public EntityModel<Boolean> logout() {
        StpUtil.logout();
        return EntityModel.of(true);
    }
}
