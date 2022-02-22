public class Test2 {

    /**
     * You are given an array. Each element represents the price of a stock on that particular day. Calculate and return the maximum profit you can make from buying and selling that stock only once.
     * 只能做多，不能做空。即只能先买后卖。
     * 输出最大利润，以及最大利润对应的买入下标和卖出下标。
     * @param args
     */
    static public int[] maxProfit(int[] prices) {
        // 当前的低价下标和高价下标
        int currentLowIdx = 0;
        int currentHighIdx = 0;
        // 最大利润对应的低价下标和高价下标
        int lowIdx = 0;
        int highIdx = 0;
        int maxProfit = 0;

        for (int i = 0; i < prices.length; ++i) {
            // 当前价格 > 当前高价
            if (prices[i] > prices[currentHighIdx]) {
                currentHighIdx = i;
                if (prices[currentHighIdx] - prices[currentLowIdx] > maxProfit) {
                    maxProfit = prices[currentHighIdx] - prices[currentLowIdx];
                    lowIdx = currentLowIdx;
                    highIdx = currentHighIdx;
                }
            }
            // 当前价格 < 当前低价。当前低价和高价的下标都向右移到当前位置
            else if (prices[i] < prices[currentLowIdx]) {
                currentLowIdx = currentHighIdx = i;
            }
        }
        return new int[]{maxProfit, lowIdx, highIdx};
    }


    static private int dpSolution(int[] prices) {
        int mp = 0;
        int minPrice = prices[0];

        for (int i = 1; i < prices.length; ++i) {
            mp = Math.max(mp, prices[i] - minPrice);
            minPrice = Math.min(minPrice, prices[i]);
        }
        return mp;
    }



    static public void main(String[] args) {
        int[] prices = new int[]{7, 1, 3, 5, 8, 4, -5, 5};
        int[] maxProfitAndIdx = maxProfit(prices);
        System.out.println("maxProfit = " + maxProfitAndIdx[0]);
        System.out.println("lowIdx = " + maxProfitAndIdx[1]);
        System.out.println("highIdx = " + maxProfitAndIdx[2]);

        System.out.println("maxProfit = " + dpSolution(prices));
    }
}
