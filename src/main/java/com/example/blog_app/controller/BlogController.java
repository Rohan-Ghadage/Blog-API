package com.example.blog_app.controller;

import com.example.blog_app.model.Blog;
import com.example.blog_app.service.BlogService;
import com.example.blog_app.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/summary/{id}")
    public ResponseEntity<String> getBlogSummary(@PathVariable Long id) {
        String summary = blogService.getSummaryById(id);
        return ResponseEntity.ok(summary);
    }


    @GetMapping("/paginated")
    public ResponseEntity<Page<Blog>> getAllBlogsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Blog> blogPage = blogService.getAllBlogsPaginated(pageable);
        return ResponseEntity.ok(blogPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id){
        Optional<Blog> blog = blogService.getBlogById(id);
        return blog.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // for creating new blog
    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
        if (blog.getTitle() == null || blog.getContent() == null || blog.getAuthor() == null) {
            return ResponseEntity.badRequest().build();
        }
        Blog savedBlog = blogService.saveBlog(blog);
        return ResponseEntity.ok(savedBlog);
    }

    // for updating an existing blog
    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable Long id, @RequestBody Blog blog){
        if (!blogService.getBlogById(id).isPresent()){
            return ResponseEntity.notFound().build();
        }
        blog.setId(id);
        Blog updatedBlog = blogService.saveBlog(blog);
        return ResponseEntity.ok(updatedBlog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable Long id) {
        if (!blogService.getBlogById(id).isPresent()) {
            return ResponseEntity.status(404).body("blog not found with ID: " + id);
        }
        blogService.deleteBlog(id);
        return ResponseEntity.ok("Blog deleted successfully with ID: " + id);
    }
}
