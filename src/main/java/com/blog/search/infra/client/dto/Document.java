package com.blog.search.infra.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record Document(String title,
                       String contents,
                       String url,
                       @JsonProperty("blogname") String blogName,
                       String thumbnail,
                       OffsetDateTime datetime
) {
}
