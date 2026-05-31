package com.siddardha_007.blog_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDto<T> {

    private List<T> posts;

    private int pageNumber;

    private int pageSize;

    private Long totalElements;

    private int totalPages;

    private boolean last;
}
