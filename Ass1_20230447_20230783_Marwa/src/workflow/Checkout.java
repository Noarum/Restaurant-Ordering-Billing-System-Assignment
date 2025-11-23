package workflow;
import order.*;
import payment.*;
import discount.*;
import receipt.*;
import menu.*;
import java.util.Scanner;

public class Checkout {
    private Order order;
    private PaymentContext paymentContext;
    private DiscountContext discountContext;
    private ReceiptGenerator receiptGenerator;
    private Scanner scanner;

    MenuFactory vegFactory = new VegMenuFactory();
    MenuFactory nonVegFactory = new NonVegMenuFactory();
    MenuFactory kidsFactory = new KidsMenuFactory();

    double totalTax;
    double total_of_discount;
    double finalAmount;

    private int positive_input() {
        int input;
        while (true) {
            input = integer_input();
            if (input > 0) {
                return input;
            } else {
                System.out.print("Quantity must be a positive number: ");
            }
        }
    }

    public Checkout(Order order, Scanner scanner) {
        this.order = order;
        this.scanner = scanner;
        this.paymentContext = new PaymentContext();
        this.discountContext = new DiscountContext();
        this.receiptGenerator = new receipt_details();
    }

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentContext.setStrategy(strategy);
    }
    public void setDiscountStrategy(DiscountStrategy strategy) {
        this.discountContext.setStrategy(strategy);
    }

    public boolean order_set() {
        boolean ordering = true;
        while (ordering) {
            addItemToOrder(order);

            System.out.print("\nDo you want to add another item? (y/n): ");
            String choice = scanner.nextLine();
            if (!choice.trim().equalsIgnoreCase("y")) {
                ordering = false;
            }
        }

        if (order.getItems().isEmpty()) {
            System.out.println("Order cancelled. No items selected.");
            return false;
        }

        order_details(order);
        payment_method(this);
        discount_type(this);

        return true;
    }

    public void checkout() {
        order_review();
        apply_discount();
        calculate_taxes();
        calculate_final_total();
        print_receipt();
        process_payment();
        finish_order();
    }


    private void order_review() {
        System.out.printf("Order Subtotal is: %.2f EGP before taxes and discounts.%n", order.subtotal());
    }

    private void apply_discount() {
        this.total_of_discount = discountContext.executeDiscount(order);
        if (this.total_of_discount > 0) {
            System.out.printf("Discount Applied: -%.2f EGP%n", total_of_discount);
        } else {
            System.out.println("No Discount Applied.");
        }
    }


    private void calculate_taxes() {
        double subtotal = order.subtotal();
        this.totalTax = (subtotal - total_of_discount) * 0.14;
        if(this.totalTax < 0) this.totalTax = 0;
        System.out.printf("Taxes Calculated (14%%): %.2f EGP%n", totalTax);
    }

    private void calculate_final_total() {
        this.finalAmount = (order.subtotal() - total_of_discount) + totalTax;
        if (this.finalAmount < 0) this.finalAmount = 0;
        System.out.printf("Final Amount is : %.2f EGP%n", this.finalAmount);
    }

    private void print_receipt() {
        System.out.println("Your detailed Receipt");
        receiptGenerator.generate(order, finalAmount, total_of_discount, totalTax);
    }

    private void process_payment() {
        System.out.println("Processing Payment...");
        paymentContext.executePayment(finalAmount);
    }

    private void finish_order() {
        System.out.println("Finishing Order Placement");
        order.placeOrder();
    }



    private void addItemToOrder(Order order) {
        System.out.println("\nPLEASE SELECT A MENU CATEGORY\n");
        System.out.println("1. Non-Vegetarian");
        System.out.println("2. Vegetarian");
        System.out.println("3. Kids Menu");
        System.out.print("Enter choice: ");

        int category_choice = integer_input();
        MenuItem baseItem = null;
        String baseItemName = null;

        switch (category_choice) {
            case 1:
                baseItem = selectNonVegItem();
                break;
            case 2:
                baseItem = selectVegItem();
                break;
            case 3:
                baseItem = selectKidsItem();
                break;
            default:
                System.out.println("Invalid category selection, returning to main menu.");
                return;
        }


        if (baseItem != null) {
            baseItemName = baseItem.getName();

            System.out.print("Enter Quantity (minimum 1): ");
            int quantity = positive_input();

            if (quantity > 0) {

                for (int i = 0; i < quantity; i++) {
                    MenuItem freshBaseItem = createItemByChoice(category_choice, baseItem);
                    if (freshBaseItem == null) continue;
                    MenuItem itemWithAddOns = offerAddOns(freshBaseItem);
                    order.addItem(itemWithAddOns);
                }
                System.out.println(">> Added " + quantity + " x " + baseItemName + (quantity > 1 ? "s" : "") + " to order.");
            } else {
                System.out.println("Quantity must be positive, Item not added.");
            }
        }
    }


    private MenuItem createItemByChoice(int category_choice, MenuItem sampleItem) {
        String baseName = sampleItem.getName();

        if (category_choice == 1) {
            if (baseName.contains("Chicken Italian")) return nonVegFactory.createPizza("chicken italian");
            if (baseName.contains("Sausage American")) return nonVegFactory.createPizza("sausage american");
            if (baseName.contains("Beef Burger")) return nonVegFactory.createBurger("beef burger");
            if (baseName.contains("Chicken Burger")) return nonVegFactory.createBurger("chicken burger");
        }
        else if (category_choice == 2) {
            if (baseName.contains("Italian Veg")) return vegFactory.createPizza("italian");
            if (baseName.contains("Greek Veg")) return vegFactory.createPizza("greek");
            if (baseName.contains("Mushroom Veg Burger")) return vegFactory.createBurger("mushroom burger");
            if (baseName.contains("Lentils Veg Burger")) return vegFactory.createBurger("lentils burger");
        }
        else if (category_choice == 3) {
            if (baseName.contains("Pizza")) return kidsFactory.createPizza("");
            if (baseName.contains("Burger")) return kidsFactory.createBurger("");
        }
        return null;
    }


    private MenuItem selectNonVegItem() {
        System.out.println("\n Non-Veg Menu ");
        System.out.printf("1. Chicken Italian Pizza (%.2f EGP)%n", nonVegFactory.createPizza("chicken italian").getPrice());
        System.out.printf("2. Sausage American Pizza (%.2f EGP)%n", nonVegFactory.createPizza("sausage american").getPrice());
        System.out.printf("3. Beef Burger (%.2f EGP)%n", nonVegFactory.createBurger("beef burger").getPrice());
        System.out.printf("4. Chicken Burger (%.2f EGP)%n", nonVegFactory.createBurger("chicken burger").getPrice());
        System.out.print("Choose item: ");
        int choice = integer_input();

        switch (choice) {
            case 1: return nonVegFactory.createPizza("chicken italian");
            case 2: return nonVegFactory.createPizza("sausage american");
            case 3: return nonVegFactory.createBurger("beef burger");
            case 4: return nonVegFactory.createBurger("chicken burger");
            default: System.out.println("Invalid choice. Try again."); return null;
        }
    }

    private MenuItem selectVegItem() {
        System.out.println("\nVeg Menu");
        System.out.printf("1. Italian Veg Pizza (%.2f EGP)%n", vegFactory.createPizza("italian").getPrice());
        System.out.printf("2. Greek Veg Pizza (%.2f EGP)%n", vegFactory.createPizza("greek").getPrice());
        System.out.printf("3. Mushroom Veg Burger (%.2f EGP)%n", vegFactory.createBurger("mushroom burger").getPrice());
        System.out.printf("4. Lentils Veg Burger (%.2f EGP)%n", vegFactory.createBurger("lentils burger").getPrice());
        System.out.print("Choose item: ");
        int choice = integer_input();

        switch (choice) {
            case 1: return vegFactory.createPizza("italian");
            case 2: return vegFactory.createPizza("greek");
            case 3: return vegFactory.createBurger("mushroom burger");
            case 4: return vegFactory.createBurger("lentils burger");
            default: System.out.println("Invalid choice. Try again."); return null;
        }
    }

    private MenuItem selectKidsItem() {
        System.out.println("\nKids Menu");
        System.out.printf("1. Kids mini Pizza (%.2f EGP)%n", kidsFactory.createPizza("").getPrice());
        System.out.printf("2. Kids Burger (%.2f EGP)%n", kidsFactory.createBurger("").getPrice());
        System.out.print("Choose item: ");
        int choice = integer_input();

        switch (choice) {
            case 1: return kidsFactory.createPizza("");
            case 2: return kidsFactory.createBurger("");
            default: System.out.println("Invalid choice. Try again."); return null;
        }
    }
    private MenuItem offerAddOns(MenuItem item) {
        MenuItem currentItem = item;
        System.out.print("Add Extra Cheese (+15 EGP) to " + currentItem.getName() + "? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            currentItem = new ExtraCheese(currentItem);
        }

        System.out.print("Add Spicy Sauce (+8 EGP) to " + currentItem.getName() + "? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            currentItem = new SpicySauce(currentItem);
        }

        System.out.print("Add Extra Ranch Sauce (+10 EGP) to " + currentItem.getName() + "? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            currentItem = new RanchSauce(currentItem);
        }

        return currentItem;
    }
    private String get_address_details(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    private void order_details(Order order) {
        System.out.println("\n ORDER TYPE ");
        System.out.println("1. Dine-In");
        System.out.println("2. Takeaway");
        System.out.println("3. Delivery");
        System.out.print("Select Order Type: ");
        int type = integer_input();

        if (type == 1) {
            order.setOrderType("Dine-In");
            System.out.print("Enter Table Number: ");
            order.setTableNumber(integer_input());
        } else if (type == 2) {
            order.setOrderType("Takeaway");
        } else if (type == 3) {
            order.setOrderType("Delivery");
            String address = get_address_details("Enter Delivery Address: ");
            order.setDeliveryAddress(address);
        }
    }

    private void payment_method(Checkout process) {
        System.out.println("\nPLEASE SELECT PAYMENT METHOD");
        System.out.println("1. Credit Card");
        System.out.println("2. Cash");
        System.out.println("3. Digital Wallet");
        System.out.print("Select Payment: ");
        int choice = integer_input();

        switch (choice) {
            case 1: process.setPaymentStrategy(new credit_card_pay()); break;
            case 2: process.setPaymentStrategy(new cash_pay()); break;
            case 3: process.setPaymentStrategy(new e_wallet_pay()); break;
            default:
                System.out.println("Invalid selection. Defaulting to Cash.");
                process.setPaymentStrategy(new cash_pay());
        }
    }
    private void discount_type(Checkout process) {
        System.out.println("\nDISCOUNT");

        DiscountStrategy[] strategies = {
                new pizza_discount(),
                new meat_discount(),
                new chicken_discount()
        };

        double totalApplicableDiscount = 0.0;
        DiscountContext tempContext = new DiscountContext();

        System.out.println("Checking item categories for applicable discounts:");

        for (DiscountStrategy strategy : strategies) {
            tempContext.setStrategy(strategy);
            double currentDiscount = tempContext.executeDiscount(order);

            if (currentDiscount > 0.0) {
                String strategyName = strategy.getClass().getSimpleName();
                System.out.printf("   (Saving: %.2f EGP) applied to %s relevant items.%n",
                        currentDiscount,
                        strategyName.replace("_discount", ""));
                totalApplicableDiscount += currentDiscount;
            }
        }

        final double totalDiscountToApply = totalApplicableDiscount;

        if (totalDiscountToApply > 0) {
            DiscountStrategy combinedStrategy = (ord) -> totalDiscountToApply;
            process.setDiscountStrategy(combinedStrategy);

            System.out.printf("\nTotal Accumulated Discount: %.2f EGP%n", totalDiscountToApply);
        } else {
            process.setDiscountStrategy(DiscountStrategy.none());
            System.out.println("\nNo discounts found.");
        }
    }

    private int integer_input() {
        while (true) {
            try {
                String inputLine = scanner.nextLine().trim();
                if (inputLine.isEmpty()) {
                    System.out.print("Input cannot be empty, Please enter a number: ");
                    continue;
                }
                return Integer.parseInt(inputLine);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input, Please enter a number: ");
            }
        }
    }


}