package com.firstproject.blog_project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstproject.blog_project.TestSecurityConfig;
import com.firstproject.blog_project.model.Blog;
import com.firstproject.blog_project.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Blog blog;

    @BeforeEach
    void setUp() {
        blogRepository.deleteAll();
        blog = new Blog("John", "Test Title", "Test Topic", "Extra content", "Test Content", LocalDate.now());
        blogRepository.save(blog);
    }

    @Test
    void testGetAllBlogs() throws Exception {
        mockMvc.perform(get("/blogs")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].title", is("Test Title")));
    }

    @Test
    void testGetBlogById() throws Exception {
        mockMvc.perform(get("/blogs/" + blog.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test Title")));
    }

    @Test
    void testGetBlogById_NotFound() throws Exception {
        mockMvc.perform(get("/blogs/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateBlog_Success() throws Exception {
        Blog newBlog = new Blog("Alice", "New Title", "New Topic", "Extra", "New Content", LocalDate.now());
        mockMvc.perform(post("/blogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBlog)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("New Title")));
    }

    @Test
    void testCreateBlog_ValidationError() throws Exception {
        Blog invalidBlog = new Blog("", "", "", "", "", LocalDate.now());
        mockMvc.perform(post("/blogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidBlog)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.title").exists())
                .andExpect(jsonPath("$.errors.topic").exists())
                .andExpect(jsonPath("$.errors.content").exists());
    }

    @Test
    void testUpdateBlog_Success() throws Exception {
        blog.setTitle("Updated Title");
        mockMvc.perform(put("/blogs/" + blog.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(blog)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Title")));
    }

    @Test
    void testUpdateBlog_NotFound() throws Exception {
        Blog updated = new Blog("Name", "Updated", "Topic", "Extra", "Content", LocalDate.now());
        mockMvc.perform(put("/blogs/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteBlog_Success() throws Exception {
        mockMvc.perform(delete("/blogs/" + blog.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteBlog_NotFound() throws Exception {
        mockMvc.perform(delete("/blogs/999"))
                .andExpect(status().isNotFound());
    }
}
