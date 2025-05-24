package com.blog.search.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlUtil {

    private static final String SEARCH_URL = "/v2/search/blog";

    @Value("${kakao.url.prefix}")
    private String urlPrefix;

    public String getKakaoBlogSearchUrl() {
        return urlPrefix + SEARCH_URL;
    }
}
