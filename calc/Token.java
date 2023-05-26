package calc;

public class Token {

    public final TokenType token;
    public final String lexeme;

    public Token(TokenType token, String lexeme)
    {
        this.token = token;
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        String s = "";
        switch (token) {
            case Num -> {
                s += "Num: ";
                break;
            }
            case LParen -> {
                s += "LParen: ";
            }
            case RParen -> {
                s += "RParen: ";
            }
            case Add -> {
                s += "Add: ";
            }
            case Sub -> {
                s += "Sub: ";
            }
            case Mult -> {
                s += "Mult: ";
            }
            case Div -> {
                s += "Div: ";
            }
            case Pow -> {
                s += "Pow: ";
            }
            case EOI -> {
                s += "EOI: ";
            }
            case Invalid -> {
                s += "Invalid: ";
            }
            default -> throw new IllegalStateException("Unexpected value: " + token);
        }
        return s + lexeme;
    }
}
