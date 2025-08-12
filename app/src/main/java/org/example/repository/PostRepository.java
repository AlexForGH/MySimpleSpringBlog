package org.example.repository;

import org.example.model.Post;

import java.util.List;

public interface PostRepository {
    List<Post> getAllPosts();

    void addPost(Post post);

    void deletePostById(Long id);

    int countPosts();
}
