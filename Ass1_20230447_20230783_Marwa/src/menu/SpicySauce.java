package menu;

public class SpicySauce extends AddOnDecorator {
    public final double extra = 8.0;
    public SpicySauce(MenuItem wrapped) { super(wrapped); }
    public String getName() {
        return wrapped.getName() + " extra Spicy Sauce";
    }
    public double getPrice() {
        return wrapped.getPrice() + extra;
    }
}
