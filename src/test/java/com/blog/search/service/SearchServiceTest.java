package com.blog.search.service;

import com.blog.search.infra.client.dto.Document;
import com.blog.search.infra.client.dto.KakaoBlogSearchResult;
import com.blog.search.infra.client.dto.Meta;
import com.blog.search.service.dto.BlogResponseDto;
import com.blog.search.service.dto.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @InjectMocks
    private SearchService searchService;

    @Mock
    private BlogSearchClient blogSearchClient;

    @DisplayName("블로그 검색 테스트")
    @Test
    void searchBlogsTest() {
        KakaoBlogSearchResult searchResult = new KakaoBlogSearchResult(new Meta(10, 11, false),
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
        PageRequest pageRequest = PageRequest.of(0, 10);
        String query = "query";
        given(blogSearchClient.search(query, pageRequest)).willReturn(searchResult);
        PageResponse<BlogResponseDto> actual = searchService.searchBlogs(query, pageRequest);
        BlogResponseDto responseDto = BlogResponseDto.from(searchResult.getContents().get(0));
        PageImpl<BlogResponseDto> expected = new PageImpl<>(List.of(responseDto), pageRequest, searchResult.getTotalElementCount());
        assertThat(actual)
                .isEqualTo(PageResponse.of(expected));
    }
}
