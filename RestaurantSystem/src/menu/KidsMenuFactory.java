package menu;

// KIDS MENU FACTORY
public class KidsMenuFactory implements MenuFactory {
    public MenuItem createPizza(String variant) {
        return new BaseMenuItem("Kids mini Pizza", 87.0, "Pizza");
    }
    public MenuItem createBurger(String variant) {
        return new BaseMenuItem("Kids Burger", 62.0, "Burger");
    }
}
