package org.example.controller;

import org.example.model.Post;
import org.example.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final String uploadDir = "uploads/";

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String posts(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "posts";
    }

    @GetMapping("/add")
    public String addPost(Model model) {
        model.addAttribute("post", null);
        model.addAttribute("formAction", "/MySimpleSpringBlog/posts/add");
        return "add-post";
    }

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, Model model) {
//        Post post = postService.getPostById(id);
//        model.addAttribute("post", post);
        model.addAttribute("formAction", "/posts/edit/" + id);
        return "add-post";
    }

    @PostMapping({"/add", "/edit/{id}"})
    public String savePost(
            @PathVariable(name = "id", required = false) Long id,
            @RequestParam("title") String title,
            @RequestParam("text") String text,
            @RequestParam("tags") String tags,
            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        Post post = new Post();

        post.setTitle(title);
        post.setText(text);

        List<String> tagList = Arrays.stream(tags.split("[,\\s]+"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
        post.setTags(tagList);

        if (!imageFile.isEmpty()) {
            String filename = UUID.randomUUID() + "-" + imageFile.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = imageFile.getInputStream()) {
                Path filePath = uploadPath.resolve(filename);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                post.setImagePath("/" + uploadDir + filename);
            }
        }

        postService.addPost(post);

        return "redirect:/posts";
    }

    @PostMapping(value = "/{id}", params = "_method=delete")
    public String delete(@PathVariable(name = "id") Long id) {
        postService.deletePostById(id);
        return "redirect:/posts";
    }
}
