package com.datn.topfood.util;

import com.datn.topfood.dto.request.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtils {
    public static Pageable toPageable(PageRequest pageRequest) {
        pageRequest = ofDefault(pageRequest);
        return org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage(),
                pageRequest.getPageSize(),
                Sort.Direction.valueOf(pageRequest.getOrder()),
                pageRequest.getOrderBy()
        );
    }

    public static PageRequest ofDefault(PageRequest pageRequest) {
        if (pageRequest.getPage() == null) {
            pageRequest.setPage(1);
        }
        if (pageRequest.getPageSize() == null) {
            pageRequest.setPageSize(10);
        }
        if (pageRequest.getOrder() == null) {
            pageRequest.setOrder("DESC");
        }
        if (pageRequest.getOrderBy() == null) {
            pageRequest.setOrderBy("id");
        }
        return pageRequest;
    }
}
