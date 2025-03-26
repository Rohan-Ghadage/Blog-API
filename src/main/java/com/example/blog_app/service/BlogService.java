package com.example.blog_app.service;

import com.example.blog_app.model.Blog;
import com.example.blog_app.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private OpenAiService openAiService;

    public String getSummaryById(Long blogId) {
        Optional<Blog> blog = blogRepository.findById(blogId);
        if (blog.isPresent()) {
            String blogContent = blog.get().getContent();
            return openAiService.generateSummary(blogContent);
        }
        return "Blog not found!";
    }

    public Page<Blog> getAllBlogsPaginated(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }

    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

}
