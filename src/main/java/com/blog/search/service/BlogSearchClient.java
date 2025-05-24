package com.blog.search.service;

import com.blog.search.infra.client.dto.BlogSearchResult;
import org.springframework.data.domain.Pageable;

public interface BlogSearchClient {

    BlogSearchResult search(String query, Pageable pageable);

}
