package datastructure.skiplist.v5.enums;

public enum OrderType {

    LIMIT(0),
    MARKET(1),
    ;

    private int code;

    OrderType(int code) {
        this.code = code;
    }
}
