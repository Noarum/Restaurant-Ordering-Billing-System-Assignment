package order;
import menu.MenuItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// ---------------------------
// OBSERVABLE
// OrderSubject maintains list of observers and notifies them when order placed
// ---------------------------

public class Order {
    private int counter = 1;
    private int orderId;
    private final List<MenuItem> items = new ArrayList<>();

    private String orderType = "Dine-In"; // or Delivery, Takeaway
    //Integer accepts null
    private Integer tableNumber = null;

    // Observers
    private final List<OrderObserver> observers = new ArrayList<>();

    public Order() { this.orderId = counter++; }

    public int getOrderId() { return orderId; }

    public void addItem(MenuItem item) { items.add(item); }

    //Read-only Access
    public List<MenuItem> getItems() { return Collections.unmodifiableList(items); }

    public String getItemNames(){
        // It allows adding new parts to the same object without creating a new object each time.
        StringBuilder sb = new StringBuilder();
        for(MenuItem item : items){
            sb.append(item.getName()).append("| ");
        }
        return sb.toString();
    }

    public void setOrderType(String t) { this.orderType = t; }
    public String getOrderType() { return orderType; }

    public void setTableNumber(Integer n) { this.tableNumber = n; }
    public Integer getTableNumber() { return tableNumber; }

    // Observer methods
    public void addObserver(OrderObserver obs) { observers.add(obs); }
    public void removeObserver(OrderObserver obs) { observers.remove(obs); }

    // placeOrder triggers notifications -> example hook for kitchen/waiter
    public void placeOrder() {
        System.out.println("Placing order " + orderId + " with " +  items.size() + " items" );
        notifyObservers();
    }

    private void notifyObservers() {
        for(OrderObserver obs : observers) {
            obs.update(this);
        }
    }

    public double subtotal() {
        return items.stream().mapToDouble(MenuItem::getPrice).sum();
    }

    @Override
    public String toString() {
        return String.format("Order #%d - type: %s - items: %s - subtotal: %.2f",
                             orderId, orderType, getItemNames(), subtotal());
    }
}
