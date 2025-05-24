package com.blog.search.service.vo;

import com.blog.search.infra.client.dto.Document;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class Blog {

    private final String title;

    private final String url;

    private final String blogName;

    private final String contents;

    private final String thumbnail;

    private final OffsetDateTime datetime;

    public static Blog from(Document document) {
        return Blog.builder()
                .title(document.title())
                .url(document.url())
                .blogName(document.blogName())
                .contents(document.contents())
                .thumbnail(document.thumbnail())
                .datetime(document.datetime())
                .build();
    }
}
