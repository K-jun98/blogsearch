package com.blog.search.service;

import com.blog.search.infra.client.dto.BlogSearchResult;
import com.blog.search.service.dto.BlogResponseDto;
import com.blog.search.service.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchService {

    private final BlogSearchClient kakaoBlogSearchClient;

    public PageResponse<BlogResponseDto> searchBlogs(String query, Pageable pageable) {
        BlogSearchResult blogSearchResult = kakaoBlogSearchClient.search(query, pageable);
        List<BlogResponseDto> response = blogSearchResult.getContents()
                .stream()
                .map(BlogResponseDto::from)
                .toList();
        return PageResponse.of(new PageImpl<>(response, pageable, blogSearchResult.getTotalElementCount()));
    }
}
