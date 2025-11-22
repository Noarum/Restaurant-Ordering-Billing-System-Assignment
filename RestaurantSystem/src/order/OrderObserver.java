package order;

// ---------------------------
// OBSERVER PATTERN FOR ORDER NOTIFICATIONS
// ---------------------------

interface OrderObserver {
    void update(Order order);
}
