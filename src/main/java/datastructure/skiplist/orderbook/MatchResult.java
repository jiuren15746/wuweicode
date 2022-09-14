package datastructure.skiplist.orderbook;


import lombok.Data;
import org.testng.collections.Lists;

import java.util.List;

@Data
public class MatchResult {

    public static final int RESULT_NOT_FILLED = 0;
    public static final int RESULT_PARTIAL_FILLED = 1;
    public static final int RESULT_FULL_FILLED = 2;

    private int result;
    private List<Trade> tradeList = Lists.newArrayList();
    //========

    public void addTrade(Order takerOrder, Order makerOrder, long amountEv) {
        Trade trade = Trade.builder()
                .takerUid(takerOrder.getUid())
                .makerUid(makerOrder.getUid())
                .takerOrderId(takerOrder.getOrderId())
                .makerOrderId(makerOrder.getOrderId())
                // 取maker价格作为trade价格
                .priceEv(makerOrder.getPriceEv())
                .amountEv(amountEv)
                .build();
        tradeList.add(trade);
    }

}
