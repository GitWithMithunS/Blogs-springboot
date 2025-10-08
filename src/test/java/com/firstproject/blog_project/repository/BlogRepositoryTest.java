package com.firstproject.blog_project.repository;

import com.firstproject.blog_project.model.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class BlogRepositoryTest {

    @Autowired
    private BlogRepository blogRepository;

    @Test
    void testSaveAndFindById() {
        Blog blog = new Blog("John", "Repo Title", "Repo Topic", "Extra Content", "Repo Content", LocalDate.now());
        Blog saved = blogRepository.save(blog);

        Optional<Blog> found = blogRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Repo Title");
    }

    @Test
    void testFindAll() {
        Blog blog1 = new Blog("Alice", "Title1", "Topic1", "Extra Content1", "Content1", LocalDate.now());
        Blog blog2 = new Blog("Bob", "Title2", "Topic2", "Extra Content2", "Content2", LocalDate.now());

        blogRepository.save(blog1);
        blogRepository.save(blog2);

        assertThat(blogRepository.findAll()).hasSize(2);
    }

    @Test
    void testDelete() {
        Blog blog = new Blog("Mark", "Delete Title", "Delete Topic", "Del Extra", "Del Content", LocalDate.now());
        Blog saved = blogRepository.save(blog);
        blogRepository.delete(saved);

        Optional<Blog> found = blogRepository.findById(saved.getId());
        assertThat(found).isEmpty();
    }
}
