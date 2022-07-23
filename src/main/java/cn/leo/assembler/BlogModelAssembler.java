package cn.leo.assembler;


import cn.leo.entities.dao.Blog;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import cn.leo.controller.BlogController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BlogModelAssembler implements RepresentationModelAssembler<Blog, EntityModel<Blog>> {
    @Override
    public EntityModel<Blog> toModel(Blog entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(BlogController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(BlogController.class).all()).withRel("blogs"),
                linkTo(methodOn(BlogController.class).oneComments(entity.getId())).withRel("comments"));
    }
}
