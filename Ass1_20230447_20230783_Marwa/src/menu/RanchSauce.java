package menu;

public class RanchSauce extends AddOnDecorator {
    public final double extra = 10.0;
    public RanchSauce(MenuItem wrapped) { super(wrapped); }
    public String getName() {
        return wrapped.getName() + " Extra Ranch Sauce";
    }
    public double getPrice() {
        return wrapped.getPrice() + extra;
    }
}
