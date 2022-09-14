package datastructure.skiplist.v5.enums;

/**
 * 订单执行策略
 */
public enum ExecStrategy {

    GTC(0),
    IOC(1),
    FOK(2),
    ;

    private int code;

    ExecStrategy(int code) {
        this.code = code;
    }
}
