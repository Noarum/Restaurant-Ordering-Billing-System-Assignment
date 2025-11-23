package receipt;
import order.*;
import menu.*;

public class receipt_details implements ReceiptGenerator {
    @Override
    public void generate(Order order,
                         double grand_total,
                         double discount_amount,
                         double taxes) {
        System.out.println("\n YOUR ORDER RECEIPT ");
        System.out.println("Order ID: " + order.getOrderId());
        String table_info = (order.getTableNumber() != null) ? String.valueOf(order.getTableNumber()) : "N/A";
        System.out.println("Table:    " + table_info);

        System.out.println("Type:     " + order.getOrderType());
        if ("Delivery".equalsIgnoreCase(order.getOrderType())) {
            String address = order.getDeliveryAddress();
            System.out.println("Deliver To: " + (address != null && !address.isEmpty() ? address : "Address Not Provided"));
        }

        System.out.println("--------------------------------------");
        for (MenuItem item : order.getItems()) {
            System.out.printf(" - %-30s : %6.2f EGP%n", item.getName(), item.getPrice());
        }

        System.out.println("---------------------------------------");
        System.out.printf("Subtotal:       %8.2f EGP%n", order.subtotal());
        System.out.printf("Discount applied:      -%8.2f EGP%n", discount_amount);
        System.out.printf("Taxes (14%%):      %8.2f EGP%n", taxes);
        System.out.println("--------------------------------------");
        System.out.printf("TOTAL AMOUNT:    %8.2f EGP%n", grand_total);
    }
}