package datastructure.skiplist.orderbook.enums;

public enum OrderType {

    LIMIT(0),
    MARKET(1),
    ;

    private int code;

    OrderType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
