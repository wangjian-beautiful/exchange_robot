package test;


public class JoinTester {
    public static void main(String[] args) {
        String json = "\"symbol\": \"BTCUSDT\", // 交易对\n" +
                "  \"orderId\": 28, // 系统的订单ID\n" +
                "  \"orderListId\": -1, // OCO订单ID，否则为 -1\n" +
                "  \"clientOrderId\": \"6gCrw2kRUAF9CvJDGP16IP\", // 客户自己设置的ID\n" +
                "  \"transactTime\": 1507725176595, // 交易的时间戳\n" +
                "  \"price\": \"0.00000000\", // 订单价格\n" +
                "  \"origQty\": \"10.00000000\", // 用户设置的原始订单数量\n" +
                "  \"executedQty\": \"10.00000000\", // 交易的订单数量\n" +
                "  \"cummulativeQuoteQty\": \"10.00000000\", // 累计交易的金额\n" +
                "  \"status\": \"FILLED\", // 订单状态\n" +
                "  \"timeInForce\": \"GTC\", // 订单的时效方式\n" +
                "  \"type\": \"MARKET\", // 订单类型， 比如市价单，现价单等\n" +
                "  \"side\": \"SELL\", // 订单方向，买还是卖\n" +
                "  \"strategyId\": 1,               // 下单填了参数才会返回\n" +
                "  \"strategyType\": 1000000        // 下单填了参数才会返回\n" +
                "  \"fills\": [ // 订单中交易的信息";
        String[] split = json.split("\n");
        for (String s : split) {
            String[] split1 = s.split(":");
            String s1 = split1[0].replaceAll("\"", "");
//            System.out.println(s1);
            String[] split2 = s.split("// ");
            if (split2.length > 1) {
                String miao = split2[1].trim();
                System.out.println(String.format("//%s\nprivate String %s;\n", miao, s1));
            }

        }
    }
}