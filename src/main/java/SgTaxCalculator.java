import java.util.LinkedHashMap;
import java.util.Map;

public class SgTaxCalculator {

    // 为了避免相同金额插入map中，被认为是同一个key
    static class Amount {
        long amount;
        public Amount(long amount) {
            this.amount = amount;
        }
    }

    static public long calculateTax(long annualIncome) {
        LinkedHashMap<Amount, Double> taxRateMap = new LinkedHashMap<>();
        taxRateMap.put(new Amount(20000L), 0.00d);
        taxRateMap.put(new Amount(10000L), 0.02d);
        taxRateMap.put(new Amount(10000L), 0.035d);
        taxRateMap.put(new Amount(40000L), 0.07d);
        taxRateMap.put(new Amount(40000L), 0.115d);
        taxRateMap.put(new Amount(40000L), 0.15d);
        taxRateMap.put(new Amount(40000L), 0.18d);
        taxRateMap.put(new Amount(40000L), 0.19d);
        taxRateMap.put(new Amount(40000L), 0.195d);
        taxRateMap.put(new Amount(40000L), 0.20d);
        taxRateMap.put(new Amount(Long.MAX_VALUE), 0.22d);

        long income = annualIncome;
        long totalTax = 0L;

        for (Map.Entry<Amount, Double> entry : taxRateMap.entrySet()) {
            long amount = entry.getKey().amount;
            double rate = entry.getValue();
//            System.out.println(amount + " -> " + rate);

            if (income < amount) {
                totalTax += income * rate;
                break;
            } else {
                totalTax += amount * rate;
                income -= amount;
            }
        }
        return totalTax;
    }


    static public void main(String[] args) {

        long income = 195000;
        long tax = calculateTax(income);
        System.out.println("==========");
        System.out.println(income + " -> " + tax + " -> " + (income - tax));

        income = 255000;
        tax = calculateTax(income);
        System.out.println("==========");
        System.out.println(income + " -> " + tax + " -> " + (income - tax));

        income = 330000;
        tax = calculateTax(income);
        System.out.println("==========");
        System.out.println(income + " -> " + tax + " -> " + (income - tax));
    }
}
