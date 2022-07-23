package cn.leo.controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin
@RequestMapping
@RestController
public class BaseController {
    @GetMapping
    public EntityModel<?> all() {
        return EntityModel.of(linkTo(methodOn(BaseController.class).all()).withRel("main"),
                linkTo(methodOn(BlogController.class).all()).withRel("blogs"),
                linkTo(methodOn(CommentController.class).all()).withRel("commons"),
                linkTo(methodOn(TagController.class).all()).withRel("tags"),
                linkTo(methodOn(TypeController.class).all()).withRel("types"),
                linkTo(methodOn(UserController.class).link()).withRel("user"));
    }
}
