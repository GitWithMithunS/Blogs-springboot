package com.firstproject.blog_project.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstproject.blog_project.TestSecurityConfig;
import com.firstproject.blog_project.model.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testHandleValidationErrors() throws Exception {
        Blog invalidBlog = new Blog("John", "", "", "", "", LocalDate.now());

        mockMvc.perform(post("/blogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidBlog)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.title").value("Title is required"))
                .andExpect(jsonPath("$.errors.topic").value("Topic is required"))
                .andExpect(jsonPath("$.errors.content").value("Content is required"));
    }
}
