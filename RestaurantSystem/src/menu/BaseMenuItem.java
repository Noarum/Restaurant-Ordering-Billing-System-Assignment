package menu;

// ---------------------------
// CONCRETE MENU ITEMS (BASE)
// Concrete Component in Decorator Pattern
// Concrete Product produced by Abstract Factory
// ---------------------------

public class BaseMenuItem implements MenuItem {
    private String name;
    private double price;
    private String category;

    public BaseMenuItem(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }

    @Override
    public String toString() {
        return String.format("%s (%s) - %.2f EGP", name, category, price);
    }

}
