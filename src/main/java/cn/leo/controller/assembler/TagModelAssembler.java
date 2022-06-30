package cn.leo.controller.assembler;

import cn.leo.controller.TagController;
import cn.leo.entities.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class TagModelAssembler implements RepresentationModelAssembler<Tag, EntityModel<Tag>> {
    @Override
    public EntityModel<Tag> toModel(Tag entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(TagController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(TagController.class).all()).withRel("tags"));
    }
}
