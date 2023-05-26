package calc;

import java.util.List;

public class Parser {

    private final List<Token> tokens;

    private int currentToken;

    private static Parser parser = null;
    private Parser(List<Token> tokens)
    {
        this.tokens = tokens;
        currentToken = 0;
    }

    public static Parser parser(List<Token> tokens)
    {
        if(parser == null)
            parser =  new Parser(tokens);
        return parser;
    }
    private ASTNode parsePrimary()
    {
        Token token = tokens.get(currentToken++);

        if(token.token == TokenType.Num)
        {
            try{
                double value = (token.lexeme.charAt(0) == '-' ? -1 * Double.parseDouble(token.lexeme.substring(1)) : Double.parseDouble(token.lexeme));
                return new NumberNode(value);
            }
            catch(Exception e)
            {
                System.out.println("Error: Invalid number format");
                return null;
            }
        } else if (token.token == TokenType.LParen) {
            var expression = parseExpression();
            if(expression == null)
            {
                return null;
            }
            if (currentToken < tokens.size() && tokens.get(currentToken).token == TokenType.RParen)
            {
                ++currentToken;
                return expression;
            }
            else {
                System.out.println("Error: Missing closing parenthesis");
                return null;
            }
        }
        else {
            System.out.println("Error: Invalid factor");
            return null;
        }
    }

    private ASTNode parseTerm(){
        var result = parsePrimary();

        while (currentToken < tokens.size()){
            Token token = tokens.get(currentToken);
            if(token.token == TokenType.Mult || token.token == TokenType.Div || token.token == TokenType.Pow){
                ++currentToken;
                var factor = parsePrimary();
                if(factor == null)
                    return null;
                result = new BinaryNode(token.token, result, factor);
            }
            else
                break;
        }

        return result;
    }
    public ASTNode parseExpression()
    {
        var result = parseTerm();

        while(currentToken < tokens.size()){
            Token token = tokens.get(currentToken);
            if(token.token == TokenType.Add || token.token == TokenType.Sub){
                ++currentToken;
                var term = parseTerm();
                if (term == null) {
                    return null;
                }
                result = new BinaryNode(token.token, result, term);
            }
            else
                break;
        }
        return result;
    }
}
