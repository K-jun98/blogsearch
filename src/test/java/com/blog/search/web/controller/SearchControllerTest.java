package com.blog.search.web.controller;

import com.blog.search.service.SearchService;
import com.blog.search.service.dto.BlogResponseDto;
import com.blog.search.service.dto.PageResponse;
import com.blog.search.web.exception.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SearchService searchService;

    @Test
    @DisplayName("Search posts successfully with valid query and pagination")
    void shouldSearchPostsSuccessfully() throws Exception {
        var blogDto = BlogResponseDto.builder()
                .blogName("blogName")
                .url("http://example.com")
                .contents("contents")
                .title("title")
                .datetime(OffsetDateTime.of(2023, 10, 1, 0, 0, 0, 0, OffsetDateTime.now().getOffset()))
                .build();
        var pageResponse = new PageResponse<>(Collections.singletonList(blogDto), 0, 10, 1);

        Mockito.when(searchService.searchBlogs(eq("test-query"), any(Pageable.class)))
                .thenReturn(pageResponse);

        mockMvc.perform(get("/api/v1/search")
                        .param("query", "test-query")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pageResponse)));
    }

    @Test
    @DisplayName("Search posts fails when page number exceeds limit")
    void shouldFailWhenPageNumberExceedsLimit() throws Exception {
        ApiErrorResponse response = ApiErrorResponse.of(400, "Bad Request", "Page number must be less than 50");
        mockMvc.perform(get("/api/v1/search")
                        .param("query", "test-query")
                        .param("page", "50")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    @DisplayName("Search posts fails when query parameter is missing")
    void shouldFailWhenQueryIsMissing() throws Exception {
        mockMvc.perform(get("/api/v1/search")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
