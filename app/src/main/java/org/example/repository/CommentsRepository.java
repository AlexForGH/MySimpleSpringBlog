package org.example.repository;

import org.example.model.Comment;

import java.util.List;

public interface CommentsRepository {
    List<Comment> getCommentsByPostId(Long postId);
}
