package discount;
import order.*;

public interface DiscountStrategy {
    double calculate_discount (Order order);
    static DiscountStrategy none() {
        return (order) -> 0.0;
    }
}