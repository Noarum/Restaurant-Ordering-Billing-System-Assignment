package menu;

public class ExtraCheese extends AddOnDecorator{
    private final double extra = 15.0;
    public ExtraCheese(MenuItem wrapped) { super(wrapped); }
    public String getName() {
        return wrapped.getName() + " Extra Cheese";
    }
    public double getPrice() {
        return wrapped.getPrice() + extra;
    }
}
