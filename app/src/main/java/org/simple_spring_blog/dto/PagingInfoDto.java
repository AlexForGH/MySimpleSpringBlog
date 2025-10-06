package org.simple_spring_blog.dto;

public record PagingInfoDto(
        int pageNumber,
        int totalPages,
        int pageSize,
        boolean hasPrevious,
        boolean hasNext
) {
}
