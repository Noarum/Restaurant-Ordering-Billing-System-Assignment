package discount;
import order.*;
import menu.*;

public class meat_discount implements DiscountStrategy {
    @Override
    public double calculate_discount (Order order) {
        double base_price = 0;
        for (MenuItem item : order.getItems()) {
            if (item.getCategory().equalsIgnoreCase("Burger")) {
                base_price += item.getPrice();
            }
        }
        return base_price * 0.15;
    }
}