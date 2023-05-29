package calc;

public class Interpreter {
    public static double interpret(final ASTNode root) {
        if (root == null) return 0;
        if (root instanceof NumberNode) return ((NumberNode) root).getValue();
        if (root instanceof BinaryNode opNode) {
            double leftValue = interpret(opNode.getLeft());
            double rightValue = interpret(opNode.getRight());
            TokenType op = opNode.getToken();

            switch (op) {
                case Add -> {
                    return leftValue + rightValue;
                }
                case Sub -> {
                    return leftValue - rightValue;
                }
                case Mult -> {
                    return leftValue * rightValue;
                }
                case Div -> {
                    if (rightValue != 0)
                        return leftValue / rightValue;
                    else throw new ArithmeticException("Can not divide by zero");
                }
                case Pow -> {
                    return Math.pow(leftValue, rightValue);
                }
                default -> throw new RuntimeException("Unknown operator");
            }
        }
        throw new RuntimeException("Invalid AST node");
    }
}
