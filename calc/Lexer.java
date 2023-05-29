package calc;

public class Lexer {
    private final String input;
    private int currentIndex;

    public Lexer(String input) {
        this.input = input;
        currentIndex = 0;
    }

    public Token getNextToken() {

        if (currentIndex >= input.length())
            return new Token(TokenType.EOI, "");

        char c = input.charAt(currentIndex++);

        switch (c) {
            case '+' -> {
                return new Token(TokenType.Add, "+");
            }
            case '-' -> {
                try {
                    if (currentIndex == 0 || (!Character.isDigit(input.charAt(currentIndex - 2)) && input.charAt(currentIndex - 2) != ')' && Character.isDigit(input.charAt(currentIndex))))
                        return extractNumber(c);
                    return new Token(TokenType.Sub, "-");
                }
                catch(StringIndexOutOfBoundsException e)
                {
                    return extractNumber(c);
                }
            }
            case '*' -> {
                return new Token(TokenType.Mult, "*");
            }
            case '/' -> {
                return new Token(TokenType.Div, "/");
            }
            case '^' -> {
                return new Token(TokenType.Pow, "^");
            }
            case '(' -> {
                return new Token(TokenType.LParen, "(");
            }
            case ')' -> {
                return new Token(TokenType.RParen, ")");
            }
            case '.' -> {
                return extractNumber(c);
            }
            default -> {
                if (Character.isDigit(c))
                    return extractNumber(c);
                return new Token(TokenType.Invalid, String.valueOf(c));
            }
        }
    }

    private Token extractNumber(char currentDigit) {
        StringBuilder s = new StringBuilder();
        byte decimals = 0;
        s.append(currentDigit);
        while (currentIndex < input.length() && (Character.isDigit(input.charAt(currentIndex)) || input.charAt(currentIndex) == '.')) {
            if (input.charAt(currentIndex) == '.') {
                decimals++;
                if (decimals >= 2)
                    break;
            }
            s.append(input.charAt(currentIndex++));
        }
        return new Token(TokenType.Num, s.toString());
    }
}
