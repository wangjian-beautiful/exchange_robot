package com.bjs.hedge.crud.service;
import com.bjs.hedge.crud.model.ExHedgeOrder;
import com.bjs.hedge.crud.Service;



/**
 * @author AUTHOR
 * @date  2022/09/07
 */
public interface ExHedgeOrderService extends Service<ExHedgeOrder> {

    ExHedgeOrder getExHedgeOrderByUniqueKey(String table,Long uniqueKey);
}
