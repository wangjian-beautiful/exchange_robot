package com.bjs.hedge.common.enums;

/**
 * 有效方式 (timeInForce):
 */
public enum TimeInForceStatus {
    /*
    * GTC	成交为止
            订单会一直有效，直到被成交或者取消。
      IOC	无法立即成交的部分就撤销
            订单在失效前会尽量多的成交。
      FOK	无法全部立即成交就撤销
            如果无法全部成交，订单会失效。
    * */
    GTC(null),
    IOC("IMMEDIATE_OR_CANCEL"),
    FOK("FILL_OR_KILL");

    private String code;

    TimeInForceStatus(String code){
        this.code=code;
    }

    public String getCode() {
        return code;
    }

    public static TimeInForceStatus getTimeInForceStatus(String name){
        for (TimeInForceStatus forceStatus : TimeInForceStatus.values()) {
            if(forceStatus.name().equals(name)){
                return forceStatus;
            }
        }
        return null;
    }
}
