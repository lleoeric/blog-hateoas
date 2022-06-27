package cn.leo.controller.assembler;

import cn.leo.entities.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public class TagModelAssembler implements RepresentationModelAssembler<Tag, EntityModel<Tag>> {
    @Override
    public EntityModel<Tag> toModel(Tag entity) {
        return null;
    }
}
