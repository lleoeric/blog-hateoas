package cn.leo.controller;

import cn.leo.controller.assembler.TagModelAssembler;
import cn.leo.entities.Tag;
import cn.leo.exception.TagNotFoundException;
import cn.leo.repository.TagRepository;
import cn.leo.service.TagService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("tag")
public class TagController {
    private final TagRepository tagRepository;
    private final TagService tagService;
    private final TagModelAssembler tagModelAssembler;

    TagController(TagRepository tagRepository,
                  TagService tagService,
                  TagModelAssembler tagModelAssembler) {
        this.tagRepository = tagRepository;
        this.tagService = tagService;
        this.tagModelAssembler = tagModelAssembler;
    }

    @GetMapping("/tags/{id}")
    public EntityModel<Tag> one(@PathVariable Integer id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(id));
        return tagModelAssembler.toModel(tag);
    }
    @GetMapping("/tags")
    public CollectionModel<EntityModel<Tag>> all(){
        List<EntityModel<Tag>> tags = tagRepository.findAll().stream()
                .map(tagModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(tags,linkTo(methodOn(TagController.class).all()).withSelfRel());
    }


}
