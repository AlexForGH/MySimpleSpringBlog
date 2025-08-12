package org.example.service;

import org.example.model.Comment;
import org.example.model.Post;
import org.example.repository.CommentsRepository;
import org.example.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentsRepository commentsRepository;

    @Autowired
    public PostService(PostRepository postRepository, CommentsRepository commentsRepository) {
        this.postRepository = postRepository;
        this.commentsRepository = commentsRepository;
    }

    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.getAllPosts();
        posts.forEach(post -> {
            post.setComments(getCommentsByPostId(post.getId()));
        });
        return posts;
    }

    public int countPosts() {
        return postRepository.countPosts();
    }

    public void addPost(Post post) {
        postRepository.addPost(post);
    }

    public void deletePostById(Long id) {
        postRepository.deletePostById(id);
    }

    private List<Comment> getCommentsByPostId(Long id) {
        List<Comment> comments = commentsRepository.getCommentsByPostId(id);
        if (comments.isEmpty()) return List.of();
        return comments;
    }
}