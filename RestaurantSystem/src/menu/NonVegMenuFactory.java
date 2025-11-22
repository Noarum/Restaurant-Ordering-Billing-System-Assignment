package menu;

// NON-VEG MENU FACTORY
public class NonVegMenuFactory implements MenuFactory {
    // Factory Method inside each implementation creates concrete items
    public MenuItem createPizza(String variant) {
        switch (variant.toLowerCase()) {
            case "chicken italian":
                return new BaseMenuItem("Chicken Italian Pizza", 160.0, "Pizza");
            case "sausage american":
                return new BaseMenuItem("Sausage American Pizza", 130.0, "Pizza");
            case "turkey greek":
                return new BaseMenuItem("Turkey Greek Pizza", 140.0, "Pizza");
            default:
                return new BaseMenuItem("Classic Meat Pizza", 125.0, "Pizza");
        }
    }

    public MenuItem createBurger(String variant) {
        switch (variant.toLowerCase()) {
            case "chicken burger":
                return new BaseMenuItem("Chicken Burger", 160.0, "Burger");
            case "beef burger":
                return new BaseMenuItem("Beef Burger", 145.0, "Burger");
            default:
                return new BaseMenuItem("Classic Meat Burger", 100.0, "Burger");
        }
    }

}

