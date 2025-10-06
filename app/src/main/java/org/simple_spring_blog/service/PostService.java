package org.simple_spring_blog.service;

import org.simple_spring_blog.dto.PostDto;
import org.simple_spring_blog.model.Comment;
import org.simple_spring_blog.model.Post;
import org.simple_spring_blog.repository.CommentsRepository;
import org.simple_spring_blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentsRepository commentsRepository;

    @Autowired
    public PostService(PostRepository postRepository, CommentsRepository commentsRepository) {
        this.postRepository = postRepository;
        this.commentsRepository = commentsRepository;
    }

    public Page<Post> getAllPostsOrByTagWithPagination(String searchTag, int pageNumber, int pageSize) {
        List<Post> posts;
        if (searchTag == null || searchTag.isEmpty()) posts = getAllPosts();
        else posts = getPostsByTag(searchTag);
        return paginateList(posts, pageNumber, pageSize);
    }

    private Page<Post> paginateList(List<Post> posts, int pageNumber, int pageSize) {
        int totalItems = posts.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        if (pageNumber < 1) pageNumber = 1;
        if (pageNumber > totalPages && totalPages > 0) pageNumber = totalPages;

        int fromIndex = (pageNumber - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalItems);

        if (fromIndex >= totalItems)
            return new PageImpl<>(List.of(), PageRequest.of(pageNumber - 1, pageSize), totalItems);

        List<Post> pageContent = posts.subList(fromIndex, toIndex);
        return new PageImpl<>(pageContent, PageRequest.of(pageNumber - 1, pageSize), totalItems);
    }

    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.getAllPosts();
        posts.forEach(post -> post.setComments(getCommentsByPostId(post.getId())));
        return posts;
    }

    public Post getPostById(Long id) {
        Post post = postRepository.getPostById(id);
        post.setComments(getCommentsByPostId(id));
        return post;
    }

    public List<Post> getPostsByTag(String tag) {
        List<Post> allPosts = getAllPosts();
        return allPosts
                .stream()
                .filter(post -> post.getTags().contains(tag))
                .toList();
    }

    public void addPost(PostDto postDto, String imagePath) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setImagePath(imagePath);
        post.setText(postDto.getText());
        post.setTags(
                Arrays
                        .stream(postDto.getTags().split("[,\\s]+"))
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList())
        );
        postRepository.addPost(post);
    }

    public void editPost(Post post) {
        postRepository.editPost(post);
    }

    public void deletePostById(Long id) {
        postRepository.deletePostById(id);
    }

    public void addComment(Comment comment) {
        commentsRepository.addComment(comment);
    }

    public void editComment(Comment comment) {
        commentsRepository.editComment(comment);
    }

    public void deleteCommentById(Long id) {
        commentsRepository.deleteCommentById(id);
    }

    private List<Comment> getCommentsByPostId(Long id) {
        List<Comment> comments = commentsRepository.getCommentsByPostId(id);
        if (comments.isEmpty()) return List.of();
        return comments;
    }
}