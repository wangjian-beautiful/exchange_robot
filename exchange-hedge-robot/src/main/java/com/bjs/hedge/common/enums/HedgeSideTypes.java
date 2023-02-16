package com.bjs.hedge.common.enums;

/**
 * 币安买卖方向类型
 */
public enum HedgeSideTypes {
    BUY,SELL;

    public static HedgeSideTypes getSideTypes(String name){
        for (HedgeSideTypes types : HedgeSideTypes.values()) {
            if (types.name().equals(name)){
                return types;
            }
        }
        return null;
    }
}
