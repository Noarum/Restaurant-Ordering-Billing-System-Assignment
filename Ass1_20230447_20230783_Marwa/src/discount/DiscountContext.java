package discount;
import order.Order;

public class DiscountContext {
    private DiscountStrategy strategy;
    public DiscountContext() {
        this.strategy = DiscountStrategy.none();
    }
    public void setStrategy(DiscountStrategy strategy) {
        this.strategy = strategy;
    }
    public double executeDiscount(Order order) {
        return strategy.calculate_discount(order);
    }
}