package cn.leo.assembler;

import cn.leo.controller.TypeController;
import cn.leo.entities.dao.Type;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TypeModelAssembler implements RepresentationModelAssembler<Type, EntityModel<Type>> {
    @Override
    public EntityModel<Type> toModel(Type entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(TypeController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(TypeController.class).all()).withRel("types"));
    }
}
