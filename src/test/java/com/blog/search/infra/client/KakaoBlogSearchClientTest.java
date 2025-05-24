package com.blog.search.infra.client;

import com.blog.search.infra.client.dto.Document;
import com.blog.search.infra.client.dto.KakaoBlogSearchResult;
import com.blog.search.infra.client.dto.Meta;
import com.blog.search.service.BlogSortType;
import com.blog.search.util.UrlUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class KakaoBlogSearchClientTest {

    @InjectMocks
    private KakaoBlogSearchClient kakaoBlogSearchClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UrlUtil urlUtil;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(kakaoBlogSearchClient, "restKey", "restKey");
    }

    @DisplayName("Kakao 블로그 검색 API 호출 테스트")
    @Test
    void searchTest() {
        PageRequest pageable = PageRequest.of(0, 10);
        given(urlUtil.getKakaoBlogSearchUrl()).willReturn("http://mocked-url.com");
        URI uri = UriComponentsBuilder
                .fromUriString(urlUtil.getKakaoBlogSearchUrl())
                .queryParam("query", "query")
                .queryParam("page", 1)
                .queryParam("size", pageable.getPageSize())
                .queryParam("sort", BlogSortType.valueBy(pageable.getSort()).getKakaoSort())
                .build(false)
                .encode()
                .toUri();
        KakaoBlogSearchResult response = new KakaoBlogSearchResult(new Meta(10, 11, false),
                new Document[]{
                        new Document(
                                "title",
                                "contents",
                                "url",
                                "blogName",
                                "thumbnail",
                                OffsetDateTime.of(2023, 10, 1, 0, 0, 0, 0, ZoneOffset.of("+09:00"))
                        )
                });
        given(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), any(), eq(KakaoBlogSearchResult.class)))
                .willReturn(new ResponseEntity<>(response, HttpHeaders.EMPTY, 200));
        KakaoBlogSearchResult result = kakaoBlogSearchClient.search("query", pageable);
      assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(response);
    }

}
