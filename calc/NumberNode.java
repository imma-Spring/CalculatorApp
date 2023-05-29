package calc;

public class NumberNode extends ASTNode {
    private final double value;

    public NumberNode(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
