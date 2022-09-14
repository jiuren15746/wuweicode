package datastructure.skiplist.orderbook.enums;

/**
 * 订单执行策略
 */
public enum ExecStrategyEnum {

    GTC(0),
    IOC(1),
    FOK(2),
    ;

    private int code;

    ExecStrategyEnum(int code) {
        this.code = code;
    }

    public static ExecStrategyEnum getByCode(int code) {
        switch (code) {
            case 0:
                return GTC;
            case 1:
                return IOC;
            case 2:
                return FOK;
            default:
                return null;
        }
    }

    public int getCode() {
        return code;
    }
}
