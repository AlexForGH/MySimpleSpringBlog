package org.example.repository;

import org.example.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class JdbcNativePostRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    private final String dbName = "posts";

    @Autowired
    public JdbcNativePostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Post> getAllPosts() {
        return jdbcTemplate.query(
                "select id, title, imagePath, likesCount, text, tags from " + dbName,
                (rs, rowNum) -> new Post(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("imagePath"),
                        rs.getInt("likesCount"),
                        rs.getString("text"),
                        fromDatabaseValue(rs.getString("tags"))
                ));
    }

    @Override
    public void addPost(Post post) {
        jdbcTemplate.update(
                "insert into " + dbName + "(title, imagePath, likesCount, text, tags) values(?, ?, ?, ?, ?)",
                post.getTitle(),
                post.getImagePath(),
                post.getLikesCount(),
                post.getText(),
                toDatabaseValue(post.getTags())
        );
    }

    @Override
    public void deletePostById(Long id) {
        jdbcTemplate.update(
                "delete from " + dbName + " where id = ?", id
        );
    }

    @Override
    public int countPosts() {
        return jdbcTemplate.queryForObject(
                "select count(*) from " + dbName, Integer.class
        );
    }

    private String toDatabaseValue(List<String> tags) {
        return (tags == null || tags.isEmpty()) ? "" : String.join(",", tags);
    }

    private List<String> fromDatabaseValue(String dbData) {
        return (dbData == null || dbData.isEmpty()) ? List.of() : Arrays.asList(dbData.split(","));
    }
}
