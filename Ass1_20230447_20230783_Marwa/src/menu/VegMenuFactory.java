package menu;

// VEG MENU FACTORY
public class VegMenuFactory implements MenuFactory {
    // Factory Method inside each implementation creates concrete items
    public MenuItem createPizza(String variant) {
        switch (variant.toLowerCase()) {
            case "italian":
                return new BaseMenuItem("Italian Veg Pizza", 150.0, "Pizza");
            case "american":
                return new BaseMenuItem("American Veg Pizza", 110.0, "Pizza");
            case "greek":
                return new BaseMenuItem("Greek Veg Pizza", 130.0, "Pizza");
            default:
                return new BaseMenuItem("Classic Veg Pizza", 100.0, "Pizza");
        }
    }

    public MenuItem createBurger(String variant) {
        switch (variant.toLowerCase()) {
            case "mushroom burger":
                return new BaseMenuItem("Mushroom Veg Burger", 130.0, "Burger");
            case "chickpeas burger":
                return new BaseMenuItem("Chickpeas Veg Burger", 128.0, "Burger");
            case "lentils burger":
                return new BaseMenuItem("Lentils Veg Burger", 115.0, "Burger");
            default:
                return new BaseMenuItem("Classic Veg Burger", 90.0, "Burger");
        }
    }

}
