package org.simple_spring_blog.repository;

import org.simple_spring_blog.model.Comment;

import java.util.List;

public interface CommentsRepository {
    List<Comment> getCommentsByPostId(Long postId);
    Comment getCommentById(Long id);
    void addComment(Comment comment);
    void editComment(Comment comment);
    void deleteCommentById(Long id);
}
