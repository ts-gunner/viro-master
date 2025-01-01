package com.forty.viro.common.dto;

import com.forty.viro.common.constant.GlobalConstant;
import lombok.Data;

@Data
public class PageRequest {

    /**
     * 当前页
     */
    private int currentPage = 1;

    /**
     * 页面大小
     */
    private int pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序， 默认升序
     */
    private String sortOrder = GlobalConstant.SORT_ORDER_ASC;
}
