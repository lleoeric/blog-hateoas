package cn.leo.controller;

import cn.leo.assembler.BlogModelAssembler;
import cn.leo.entities.dao.Blog;
import cn.leo.entities.dao.Comment;
import cn.leo.entities.dao.Tag;
import cn.leo.exception.BlogNotFoundException;
import cn.leo.service.domain.impl.BlogServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/blogs")
@CrossOrigin()
public class BlogController {

    private final BlogServiceImpl blogService;

    private final BlogModelAssembler blogModelAssembler;

    BlogController(BlogServiceImpl blogService, BlogModelAssembler blogModelAssembler) {
        this.blogService = blogService;
        this.blogModelAssembler = blogModelAssembler;
    }

    @GetMapping("/{id}")
    public EntityModel<Blog> one(@PathVariable Integer id) {

        Blog employee = blogService.findById(id) //
                .orElseThrow(() -> new BlogNotFoundException(id));


        return blogModelAssembler.toModel(employee);

    }

    @GetMapping("/{id}/comments")
    public CollectionModel<Comment> oneComments(@PathVariable Integer id) {

        List<Tag> tags = blogService.findTagsById(id);
        System.out.println(tags);
        return null;

    }

    @GetMapping("/{id}/tags")
    public CollectionModel<Tag> oneTags(@PathVariable Integer id) {

        List<Tag> tags = blogService.findTagsById(id);
        System.out.println(tags);
        return CollectionModel.of(tags,
                linkTo(methodOn(BlogController.class)
                        .oneTags(id)).withSelfRel());

    }

    @GetMapping
    public CollectionModel<EntityModel<Blog>> all() {

        List<EntityModel<Blog>> blogs = blogService.findAll().stream() //
                .map(blogModelAssembler::toModel) //
                .collect(Collectors.toList());
        blogs.stream().filter((res) -> Objects.requireNonNull(res.getContent()).getId() <= 2).forEach(res -> System.out.println(res.getContent().getTags()));
        return CollectionModel.of(blogs,
                linkTo(methodOn(BlogController.class)
                        .all()).withSelfRel());
    }

    @PostMapping
    ResponseEntity<?> newBlog(@RequestBody Blog newEmployee) {

        EntityModel<Blog> entityModel = blogModelAssembler.toModel(blogService.save(newEmployee));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> replaceBlog(@RequestBody Blog newBlog, @PathVariable Integer id) {

        Blog updatedBlog = blogService.findById(id) //
                .map(blog -> {
                    BeanUtils.copyProperties(newBlog, blog);
                    return blogService.save(blog);
                })
                .orElseGet(() -> {
                    newBlog.setId(id);
                    return blogService.save(newBlog);
                });

        EntityModel<Blog> entityModel = blogModelAssembler.toModel(updatedBlog);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @PatchMapping("/{id}")
    ResponseEntity<?> patchBlog(@RequestBody Blog newBlog, @PathVariable Integer id) {

        Blog updatedBlog = blogService.findById(id) //
                .map(blog -> {
                    BeanUtils.copyProperties(newBlog, blog);
                    return blogService.save(blog);
                })
                .orElseGet(() -> {
                    newBlog.setId(id);
                    return blogService.save(newBlog);
                });

        EntityModel<Blog> entityModel = blogModelAssembler.toModel(updatedBlog);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }
}
