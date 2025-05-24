package com.blog.search.infra.client;

import com.blog.search.infra.client.dto.KakaoBlogSearchResult;
import com.blog.search.service.BlogSearchClient;
import com.blog.search.service.BlogSortType;
import com.blog.search.util.UrlUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Getter
@Component
@RequiredArgsConstructor
public final class KakaoBlogSearchClient implements BlogSearchClient {

    private static final String KAKAO_AUTH_PREFIX = "KakaoAK ";

    private static final int KAKAO_START_PAGE_INDEX = 1;

    private final RestTemplate blogSeaerchRestTemplate;

    private final UrlUtil urlUtil;

    @Value("${kakao.rest-key}")
    private String restKey;


    @Override
    public KakaoBlogSearchResult search(String query, Pageable pageable) {
        int page = pageable.getPageNumber() + KAKAO_START_PAGE_INDEX;
        int size = pageable.getPageSize();

        URI uri = UriComponentsBuilder
                .fromUriString(urlUtil.getKakaoBlogSearchUrl())
                .queryParam("query", query)
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("sort", BlogSortType.valueBy(pageable.getSort()).getKakaoSort())
                .build(false)
                .encode()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, KAKAO_AUTH_PREFIX + restKey);

        return blogSeaerchRestTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), KakaoBlogSearchResult.class)
                .getBody();
    }
}
