package cn.leo.controller.assembler;

import cn.leo.controller.BlogController;
import cn.leo.entities.Blog;
import cn.leo.entities.Comment;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommentModelAssembler implements RepresentationModelAssembler<Comment, EntityModel<Comment>> {
    @Override
    public EntityModel<Comment> toModel(Comment entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(BlogController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(BlogController.class).all()).withRel("comments"));
    }
}
