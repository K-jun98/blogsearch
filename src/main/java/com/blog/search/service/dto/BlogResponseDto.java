package com.blog.search.service.dto;

import com.blog.search.service.vo.Blog;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record BlogResponseDto(
        String title,
        String url,
        String blogName,
        String contents,
        String thumbnail,
        OffsetDateTime datetime
) {

    public static BlogResponseDto from(Blog blog) {
        return new BlogResponseDto(
                blog.getTitle(),
                blog.getUrl(),
                blog.getBlogName(),
                blog.getContents(),
                blog.getThumbnail(),
                blog.getDatetime()
        );
    }
}
