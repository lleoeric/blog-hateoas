package cn.leo.controller;

import cn.leo.controller.assembler.BlogModelAssembler;
import cn.leo.entities.Blog;
import cn.leo.exception.BlogNotFoundException;
import cn.leo.repository.BlogRepository;
import cn.leo.service.domain.BlogService;
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
public class BlogController {
    private final BlogRepository blogRepository;
    private final BlogService blogService;
    private final BlogModelAssembler blogModelAssembler;

    BlogController(BlogService blogService, BlogModelAssembler blogModelAssembler, BlogRepository blogRepository) {
        this.blogService = blogService;
        this.blogModelAssembler = blogModelAssembler;
        this.blogRepository = blogRepository;
    }

    @GetMapping("/blogs/{id}")
    public EntityModel<Blog> one(@PathVariable Integer id) {

        Blog employee = blogRepository.findById(id) //
                .orElseThrow(() -> new BlogNotFoundException(id));
        System.out.println(employee);
        return blogModelAssembler.toModel(employee);

    }

    @GetMapping("/blogs")
    public CollectionModel<EntityModel<Blog>> all() {

        List<EntityModel<Blog>> blogs = blogRepository.findAll().stream() //
                .map(blogModelAssembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(blogs,
                linkTo(methodOn(BlogController.class)
                        .all()).withSelfRel());
    }

    @PostMapping("/blogs")
    ResponseEntity<?> newBlog(@RequestBody Blog newEmployee) {

        EntityModel<Blog> entityModel = blogModelAssembler.toModel(blogRepository.save(newEmployee));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @PutMapping("/blogs/{id}")
    ResponseEntity<?> replaceBlog(@RequestBody Blog newBlog, @PathVariable Integer id) {

        Blog updatedBlog = blogRepository.findById(id) //
                .map(blog -> {
                    BeanUtils.copyProperties(newBlog, blog);
                    return blogRepository.save(blog);
                })
                .orElseGet(() -> {
                    newBlog.setId(id);
                    return blogRepository.save(newBlog);
                });

        EntityModel<Blog> entityModel = blogModelAssembler.toModel(updatedBlog);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }
}
