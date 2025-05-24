package com.blog.search.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum BlogSortType {

    ACCURACY("accuracy"),
    RECENCY("recency");

    private final String kakaoSort;

    public static BlogSortType valueBy(Sort sort) {
        if (sort.isUnsorted()) {
            return ACCURACY;
        }
        Sort.Order order = sort.stream().findFirst().orElseGet(() -> Sort.Order.by(ACCURACY.kakaoSort));
        return Arrays.stream(values())
                .filter(value -> value.kakaoSort.equalsIgnoreCase(order.getProperty()))
                .findFirst()
                .orElse(ACCURACY);
    }
}
