package com.jeesuite.admin.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Auther: Doctor
 * @Date: 2022/3/15 18:57
 * @Description:
 */
@Data
@Builder
public class TradeBatchAddVo {

    private List<TradeAddVo> trades;
}
