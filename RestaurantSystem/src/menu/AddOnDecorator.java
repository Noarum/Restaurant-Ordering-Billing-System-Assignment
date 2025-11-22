package menu;

// ---------------------------
// DECORATOR PATTERN FOR ADD-ONS
// AddOnDecorator wraps a MenuItem and modifies price/name
// ---------------------------

public abstract class AddOnDecorator implements MenuItem{
    protected final MenuItem wrapped;
    public AddOnDecorator(MenuItem wrapped) {
        this.wrapped = wrapped;
    }
    public String getCategory() {
        return wrapped.getCategory();
    }
}
