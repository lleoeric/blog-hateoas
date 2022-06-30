package cn.leo.controller;

import cn.leo.controller.assembler.TagModelAssembler;
import cn.leo.controller.assembler.TypeModelAssembler;
import cn.leo.entities.Tag;
import cn.leo.entities.Type;
import cn.leo.exception.TagNotFoundException;
import cn.leo.repository.TagRepository;
import cn.leo.repository.TypeRepository;
import cn.leo.service.TagService;
import cn.leo.service.TypeService;
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

@RestController
public class TypeController {
    private final TypeRepository typeRepository;
    private final TypeService typeService;
    private final TypeModelAssembler typeModelAssembler;

    TypeController(TypeRepository typeRepository,
                   TypeService typeService,
                   TypeModelAssembler typeModelAssembler) {
        this.typeRepository = typeRepository;
        this.typeService = typeService;
        this.typeModelAssembler = typeModelAssembler;
    }

    @GetMapping("/types/{id}")
    public EntityModel<Type> one(@PathVariable Integer id) {
        Type tag = typeRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(id));
        return typeModelAssembler.toModel(tag);
    }
    @GetMapping("/types")
    public CollectionModel<EntityModel<Type>> all(){
        List<EntityModel<Type>> tags = typeRepository.findAll().stream()
                .map(typeModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(tags,linkTo(methodOn(TypeController.class).all()).withSelfRel());
    }
    @PostMapping("/types")
    ResponseEntity<?> newType(@RequestBody Type newType) {

        EntityModel<Type> entityModel = typeModelAssembler.toModel(typeRepository.save(newType));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @PutMapping("/types/{id}")
    ResponseEntity<?> replaceType(@RequestBody Type newType, @PathVariable Integer id) {

        Type updatedType = typeRepository.findById(id) //
                .map(tag -> {
                    BeanUtils.copyProperties(newType, tag);
                    return typeRepository.save(tag);
                })
                .orElseGet(() -> {
                    newType.setId(id);
                    return typeRepository.save(newType);
                });

        EntityModel<Type> entityModel = typeModelAssembler.toModel(updatedType);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }
}
