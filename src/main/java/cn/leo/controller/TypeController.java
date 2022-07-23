package cn.leo.controller;

import cn.leo.assembler.TypeModelAssembler;
import cn.leo.entities.dao.Type;
import cn.leo.exception.TagNotFoundException;
import cn.leo.repository.TypeRepository;
import cn.leo.service.domain.TypeService;
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
@RequestMapping("/types")
@CrossOrigin()
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

    @GetMapping("/{id}")
    public EntityModel<Type> one(@PathVariable Integer id) {
        Type tag = typeRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(id));
        return typeModelAssembler.toModel(tag);
    }
    @GetMapping
    public CollectionModel<EntityModel<Type>> all(){
        List<EntityModel<Type>> tags = typeRepository.findAll().stream()
                .map(typeModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(tags,linkTo(methodOn(TypeController.class).all()).withSelfRel());
    }
    @PostMapping
    ResponseEntity<?> newType(@RequestBody Type newType) {

        EntityModel<Type> entityModel = typeModelAssembler.toModel(typeRepository.save(newType));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @PutMapping("/{id}")
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
