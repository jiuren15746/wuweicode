package datastructure.skiplist.orderbook.enums;

public enum OrderTypeEnum {

    LIMIT(0),
    MARKET(1),
    ;

    private int code;

    OrderTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
