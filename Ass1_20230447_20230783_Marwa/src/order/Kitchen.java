package order;

public class Kitchen implements OrderObserver {
    public void update(Order order) {
        System.out.println("[Kitchen] New order received -> " +
                            order.getOrderId() + ", items: " +
                            order.getItemNames());
    }
}
