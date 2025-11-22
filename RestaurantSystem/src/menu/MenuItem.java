package menu;

// ---------------------------
// MENU ITEM INTERFACE
// Component Interface in Decorator Pattern
// Product Interface in Abstract Factory Pattern
// ---------------------------
public interface MenuItem {
    String getName();
    double getPrice();
    String getCategory(); // -> Burger or Pizza
}
