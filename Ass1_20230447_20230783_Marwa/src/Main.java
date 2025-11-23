import java.util.Scanner;
import order.*;
import menu.*;
import workflow.*;


public class Main {
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("\nWELCOME TO OUR RESTAURANT ORDERING SYSTEM ");

        boolean running = true;

        while (running) {
            System.out.println("\n1) Create New Order");
            System.out.println("2) Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    handleNewOrder();
                    break;

                case 2:
                    running = false;
                    System.out.println(" THANK YOU FOR VISITING OUR RESTAURANT ");
                    break;

                default:
                    System.out.println("Invalid choice, try again!");
            }
        }

        scanner.close();
    }

    private static void handleNewOrder() {
        Order order = new Order();
        order.addObserver(new Kitchen());
        order.addObserver(new Waiter());

        Checkout process = new Checkout(order, scanner);
        boolean done = process.order_set();

        if (done) {
            process.checkout();
        } else {
            System.out.println("Order cancelled. Returning to main menu…");
        }
    }
}