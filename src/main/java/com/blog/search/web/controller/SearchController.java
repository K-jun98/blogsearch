package com.blog.search.web.controller;

import com.blog.search.service.SearchService;
import com.blog.search.service.dto.BlogResponseDto;
import com.blog.search.service.dto.PageResponse;
import com.blog.search.web.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public PageResponse<BlogResponseDto> searchBlogs(@RequestParam("query") String query,
                                                     @PageableDefault(size = 10, sort = "ACCURACY") Pageable pageable) {
        if (pageable.getPageNumber() > 49) {
            throw new BadRequestException("Page number must be less than 50");
        }
        return searchService.searchBlogs(query, pageable);
    }
}
