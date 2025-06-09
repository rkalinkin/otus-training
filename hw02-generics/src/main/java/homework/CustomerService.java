package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private static final Comparator<Customer> ORDER_BY_SCORE_THEN_ID =
            Comparator.comparingLong(Customer::getScores).thenComparingLong(Customer::getId);

    private final TreeMap<Customer, String> map = new TreeMap<>(ORDER_BY_SCORE_THEN_ID);

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> first = map.firstEntry();
        return first == null ? null : copy(first);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> next = map.higherEntry(customer);
        return next == null ? null : copy(next);
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Map.Entry<Customer, String> copy(Map.Entry<Customer, String> e) {
        return Map.entry(new Customer(e.getKey()), e.getValue());
    }
}
