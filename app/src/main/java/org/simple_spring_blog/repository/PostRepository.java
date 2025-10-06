package org.simple_spring_blog.repository;

import org.simple_spring_blog.model.Post;

import java.util.List;

public interface PostRepository {
    List<Post> getAllPosts();
    Post getPostById(Long id);
    void addPost(Post post);
    void editPost(Post post);
    void deletePostById(Long id);
}
