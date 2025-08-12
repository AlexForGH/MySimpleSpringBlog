package org.example.repository;

import org.example.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcNativeCommentsRepository implements CommentsRepository {

    private final JdbcTemplate jdbcTemplate;

    private final String dbName = "comments";

    @Autowired
    public JdbcNativeCommentsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return jdbcTemplate.query(
                "select content from " + dbName + " where post_id = ?",
                (rs, rowNum) -> new Comment(
                        rs.getString("content")
                ),
                postId
        );
    }
}
