package com.blog.search.infra.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Meta(@JsonProperty("total_count") Integer totalCount,
                   @JsonProperty("pageable_count") Integer pageableCount,
                   @JsonProperty("is_end") Boolean isEnd) {
}
