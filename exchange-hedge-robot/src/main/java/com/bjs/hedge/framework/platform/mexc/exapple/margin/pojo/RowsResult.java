package com.bjs.hedge.framework.platform.mexc.exapple.margin.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class RowsResult<T> {

    private List<T> rows;

    private Long total;
}