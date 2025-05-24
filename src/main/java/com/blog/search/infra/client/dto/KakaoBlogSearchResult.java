package com.blog.search.infra.client.dto;

import com.blog.search.service.vo.Blog;
import lombok.Builder;

import java.util.Arrays;
import java.util.List;

@Builder
public record KakaoBlogSearchResult(Meta meta, Document[] documents) implements BlogSearchResult {

    @Override
    public List<Blog> getContents() {
        return Arrays.stream(documents)
                .map(Blog::from)
                .toList();
    }

    @Override
    public boolean hasNext() {
        return !meta.isEnd();
    }

    @Override
    public int getSize() {
        return meta.pageableCount();
    }

    @Override
    public int getTotalElementCount() {
        return meta.totalCount();
    }
}
