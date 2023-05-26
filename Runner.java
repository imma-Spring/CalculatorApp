import calc.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args)
    {
        String input;
        System.out.print("Enter an arithmetic expression: ");
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();
        scanner.close();
        Lexer lexer = Lexer.lexer(input);
        Token token;

        List<Token> tokens = new ArrayList<>();
        while ((token = lexer.getNextToken()).token != TokenType.EOI && token.token != TokenType.Invalid) {
            tokens.add(token);
        }

        Parser parser = Parser.parser(tokens);
        ASTNode ast = parser.parseExpression();

        for(final Token tok : tokens)
            System.out.println(tok);

        printAST(ast, 0);
        double result = Interpreter.interpret(ast);
        System.out.println("Result: " + result);
    }

    static void printAST(final ASTNode node, int level)
    {
        if(node == null)
            return;

        for(int i = 0; i < level; ++i)
            System.out.print("     ");

        if(node instanceof NumberNode numNode)
            System.out.println("|--- " + numNode.getValue());
        else if(node instanceof BinaryNode opNode)
        {
            System.out.println("|--- " + opNode.getToken());
            printAST(opNode.getLeft(), level + 1);
            printAST(opNode.getRight(), level + 1);
        }
    }
}
