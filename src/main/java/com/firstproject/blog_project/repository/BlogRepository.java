package com.firstproject.blog_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.firstproject.blog_project.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {

}
