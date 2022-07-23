package cn.leo.assembler;

import cn.leo.controller.CommentController;
import cn.leo.entities.dao.Comment;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommentModelAssembler implements RepresentationModelAssembler<Comment, EntityModel<Comment>> {
    @Override
    public EntityModel<Comment> toModel(Comment entity) {
        System.out.println(entity);
        return EntityModel.of(entity,
                linkTo(methodOn(CommentController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(CommentController.class).all()).withRel("comments"));
    }
}
