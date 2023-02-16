package com.bjs.hedge.framework.platform.mexc.exapple.margin.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class IsolatedMarginDataResult {
    private String symbol;
    private String leverage;
    private List<IsolatedMarginDataItem> data;
}
