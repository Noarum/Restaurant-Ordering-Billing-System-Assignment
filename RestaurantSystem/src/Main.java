import menu.*;
import order.*;

public class Main {
    public static void main(String[] args) {

        // FACTORY PATTERN
        MenuFactory vegFactory = new VegMenuFactory();
        MenuFactory nonVegFactory = new NonVegMenuFactory();

        // Items
        MenuItem italianPizza = vegFactory.createPizza("Italian");
        MenuItem classicBurger = nonVegFactory.createBurger("Classic");

        // DECORATOR PATTERN → Add-ons
        MenuItem pizzaWithCheese = new ExtraCheese(italianPizza);
        MenuItem burgerWithSauce = new SpicySauce(classicBurger);

        // ORDER CREATION + OBSERVER REGISTRATION
        Order order = new Order();
        order.setOrderType("Dine-In");
        order.setTableNumber(12);

        order.addObserver(new Kitchen());  // Observer #1
        order.addObserver(new Waiter());   // Observer #2

        // ADD ITEMS TO ORDER
        order.addItem(pizzaWithCheese);
        order.addItem(burgerWithSauce);

        // DISPLAY ORDER SUMMARY
        System.out.println(order);
        System.out.println("--------------------------------");

        // PLACE ORDER → triggers observers
        order.placeOrder();

        // SUBTOTAL
        System.out.printf("Subtotal = %.2f EGP\n", order.subtotal());
    }
}
