package payment;

public class credit_card_pay implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + String.format("%.2f", amount) + " EGP using Credit Card.");
    }
}