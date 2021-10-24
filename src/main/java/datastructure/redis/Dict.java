package datastructure.redis;

public class Dict {

    private DictHashTable ht0;
    private DictHashTable ht1;

    private long rehashIdx;

    public Dict() {
        this.ht0 = new DictHashTable();
        this.ht1 = new DictHashTable();
        this.rehashIdx = -1;
    }
}
