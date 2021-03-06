package cn.leo.controller;

import cn.leo.assembler.CommentModelAssembler;
import cn.leo.entities.dao.Comment;
import cn.leo.exception.CommentNotFoundException;
import cn.leo.repository.CommentRepository;
import cn.leo.service.domain.CommentService;
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
@RequestMapping("/comments")
@CrossOrigin()
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;
    private final CommentModelAssembler commentModelAssembler;

    CommentController(CommentService commentService,
                      CommentModelAssembler commentModelAssembler,
                      CommentRepository commentRepository) {
        this.commentService = commentService;
        this.commentModelAssembler = commentModelAssembler;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/{id}")
    public EntityModel<Comment> one(@PathVariable Integer id) {

        Comment employee = commentRepository.findById(id) //
                .orElseThrow(() -> new CommentNotFoundException(id));

        return commentModelAssembler.toModel(employee);

    }

    @GetMapping
    public CollectionModel<EntityModel<Comment>> all() {

        List<EntityModel<Comment>> employees = commentRepository.findAll().stream() //
                .map(commentModelAssembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(CommentController.class).all()).withSelfRel());
    }

    @PostMapping
    ResponseEntity<?> newEmployee(@RequestBody Comment newComment) {

        EntityModel<Comment> entityModel = commentModelAssembler.toModel(commentRepository.save(newComment));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @PostMapping("/{id}")
    ResponseEntity<?> newChildComments(@PathVariable Integer id, @RequestBody Comment newChildComment) {
        Comment newComment = commentService.saveChildComment(id, newChildComment);
        EntityModel<Comment> entityModel = commentModelAssembler.toModel(newComment);
        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody Comment newBlog, @PathVariable Integer id) {

        Comment updatedEmployee = commentRepository.findById(id) //
                .map(blog -> {
                    BeanUtils.copyProperties(newBlog, blog);
                    return commentRepository.save(blog);
                })
                .orElseGet(() -> {
                    newBlog.setId(id);
                    return commentRepository.save(newBlog);
                });

        EntityModel<Comment> entityModel = commentModelAssembler.toModel(updatedEmployee);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }
}
