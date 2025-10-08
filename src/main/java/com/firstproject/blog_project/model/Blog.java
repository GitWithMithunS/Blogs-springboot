package com.firstproject.blog_project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // extra DB column

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Topic is required")
    private String topic;

    @Column(name = "blog_content", length = 5000)
    private String blogContent; // extra DB column

    @Column(length = 5000)
    @NotBlank(message = "Content is required")
    private String content;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    // Constructors
    public Blog() {}

    public Blog(String name, String title, String topic, String blogContent, String content, LocalDate createdAt) {
        this.name = name;
        this.title = title;
        this.topic = topic;
        this.blogContent = blogContent;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getBlogContent() { return blogContent; }
    public void setBlogContent(String blogContent) { this.blogContent = blogContent; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }
}
