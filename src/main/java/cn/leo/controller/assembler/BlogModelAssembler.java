package cn.leo.controller.assembler;

import cn.leo.controller.BlogController;
import cn.leo.entities.Blog;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class BlogModelAssembler implements RepresentationModelAssembler<Blog, EntityModel<Blog>> {
    @Override
    public EntityModel<Blog> toModel(Blog entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(BlogController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(BlogController.class).all()).withRel("blogs"));
    }
}
