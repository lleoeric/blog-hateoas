package cn.leo.controller;

import cn.leo.controller.assembler.TagModelAssembler;
import cn.leo.entities.Blog;
import cn.leo.entities.Tag;
import cn.leo.exception.TagNotFoundException;
import cn.leo.repository.TagRepository;
import cn.leo.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("tags")
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

    @GetMapping("/{id}")
    public EntityModel<Tag> one(@PathVariable Integer id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(id));
        return tagModelAssembler.toModel(tag);
    }
    @GetMapping
    public CollectionModel<EntityModel<Tag>> all(){
        List<EntityModel<Tag>> tags = tagRepository.findAll().stream()
                .map(tagModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(tags,linkTo(methodOn(TagController.class).all()).withSelfRel());
    }
    @PostMapping
    ResponseEntity<?> newBlog(@RequestBody Tag newEmployee) {

        EntityModel<Tag> entityModel = tagModelAssembler.toModel(tagRepository.save(newEmployee));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> replaceBlog(@RequestBody Tag newTag, @PathVariable Integer id) {

        Tag updatedTag = tagRepository.findById(id) //
                .map(tag -> {
                    BeanUtils.copyProperties(newTag, tag);
                    return tagRepository.save(tag);
                })
                .orElseGet(() -> {
                    newTag.setId(id);
                    return tagRepository.save(newTag);
                });

        EntityModel<Tag> entityModel = tagModelAssembler.toModel(updatedTag);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

}
