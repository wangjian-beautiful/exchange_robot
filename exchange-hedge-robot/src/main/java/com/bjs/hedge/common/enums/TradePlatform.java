package com.bjs.hedge.common.enums;


/**
 * 下单平台
 */
public enum TradePlatform {

    BINANCE("币安","binance"),
    MEXC("抹茶","mexc");

     public String name;
     public String code;

    TradePlatform(String name,String code){
        this.name=name;
        this.code=code;
    }
    public String getName() {
        return name;
    }
    TradePlatform(String name){
        this.name=name;
    }
    TradePlatform(){

    }
    public static TradePlatform getPlatformByName(String name){
        for (TradePlatform tradePlatform : TradePlatform.values()) {
            if (tradePlatform.name.equals(name)){
                return tradePlatform;
            }
        }
        return null;
    }

}
