package menu;

// ---------------------------
// ABSTRACT FACTORY: MenuFactory
// Defines family of products (categories: pizza, burger, etc.)
// ---------------------------
public interface MenuFactory {
    MenuItem createPizza(String variant);
    MenuItem createBurger(String variant);
}
