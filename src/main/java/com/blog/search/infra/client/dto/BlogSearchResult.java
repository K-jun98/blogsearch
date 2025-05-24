package com.blog.search.infra.client.dto;

import com.blog.search.service.vo.Blog;

import java.util.List;

public sealed interface BlogSearchResult permits KakaoBlogSearchResult {

    List<Blog> getContents();

    boolean hasNext();

    int getSize();

    int getTotalElementCount();

}
