package com.bjs.hedge.crud.service;
import com.bjs.hedge.crud.model.HedgeOrigin;
import com.bjs.hedge.crud.Service;

import java.math.BigDecimal;


/**
 * @author AUTHOR
 * @date  2022/10/19
 */
public interface HedgeOriginService extends Service<HedgeOrigin> {

    BigDecimal sumBetting(String symbol);

}
