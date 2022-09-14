package datastructure.skiplist.orderbook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trade {

//    private String symbol;

    private long takerUid;
    private long makerUid;

    private String takerOrderId;
    private String makerOrderId;

    private long priceEv;
    private long amountEv;
    //========

}
