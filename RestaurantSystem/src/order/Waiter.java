package order;

public class Waiter implements OrderObserver {
    public void update(Order order) {
        System.out.println("[Waiter] Please serve order -> " +
                            order.getOrderId() + "(table: " +
                            order.getTableNumber() + ")");
    }
}
