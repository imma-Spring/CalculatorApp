package calc;

public class BinaryNode extends ASTNode {
    private final TokenType token;
    private final ASTNode left, right;

    public BinaryNode(TokenType op, ASTNode left, ASTNode right) {
        token = op;
        this.left = left;
        this.right = right;
    }

    public ASTNode getLeft() {
        return left;
    }

    public ASTNode getRight() {
        return right;
    }

    public TokenType getToken() {
        return token;
    }
}
