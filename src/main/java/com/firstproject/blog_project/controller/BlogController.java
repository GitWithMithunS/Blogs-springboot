package com.firstproject.blog_project.controller;

import com.firstproject.blog_project.model.Blog;
import com.firstproject.blog_project.repository.BlogRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    // ✅ Get all blogs with pagination & sorting
    @GetMapping
    public ResponseEntity<Page<Blog>> getAllBlogs(Pageable pageable) {
        return ResponseEntity.ok(blogRepository.findAll(pageable));
    }

    // ✅ Get blog by ID
    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id) {
        Optional<Blog> blog = blogRepository.findById(id);
        return blog.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Create blog
    @PostMapping
    public ResponseEntity<Blog> createBlog(@Valid @RequestBody Blog blog) {
        blog.setCreatedAt(LocalDate.now());
        Blog savedBlog = blogRepository.save(blog);
        return new ResponseEntity<>(savedBlog, HttpStatus.CREATED);
    }

    // ✅ Update blog
    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable Long id, @Valid @RequestBody Blog blogDetails) {
        return blogRepository.findById(id)
                .map(blog -> {
                    blog.setName(blogDetails.getName());
                    blog.setTitle(blogDetails.getTitle());
                    blog.setTopic(blogDetails.getTopic());
                    blog.setBlogContent(blogDetails.getBlogContent());
                    blog.setContent(blogDetails.getContent());
                    Blog updatedBlog = blogRepository.save(blog);
                    return ResponseEntity.ok(updatedBlog);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete blog
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        return blogRepository.findById(id)
                .map(blog -> {
                    blogRepository.delete(blog);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
