package datastructure.skiplist.orderbook.enums;

public enum DirectionEnum {

    BUY((byte) 0),
    SELL((byte) 1);

    private byte code;

    DirectionEnum(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return this.code;
    }
}
