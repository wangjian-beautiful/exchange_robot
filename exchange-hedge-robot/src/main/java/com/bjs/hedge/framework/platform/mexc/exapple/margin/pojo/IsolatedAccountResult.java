package com.bjs.hedge.framework.platform.mexc.exapple.margin.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class IsolatedAccountResult {

    private List<IsolatedAsset> assets;
//    private String totalAssetOfBtc;
//    private String totalLiabilityOfBtc;
//    private String totalNetAssetOfBtc;
}
