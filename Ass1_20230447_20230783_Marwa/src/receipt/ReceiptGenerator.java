package receipt;

import order.Order;

public interface ReceiptGenerator {
    void generate(
            Order order,
            double finalTotal,
            double discountAmount,
            double taxAmount
    );
}