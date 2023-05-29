package app;

import calc.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.setAll;
import static javax.swing.SwingUtilities.invokeLater;

public class Calculator extends JFrame {
    private final JTextField display;
    private final JButton[] numberButtons;
    private final JButton add;
    private final JButton subtract;
    private final JButton multiply;
    private final JButton divide;
    private final JButton pow;
    private final JButton equals;
    private final JButton decimal;
    private final JButton clear;
    private String value;
    private boolean state;
    private ASTNode ast;
    private Parser parser;
    private List<Token> tokens;
    private Lexer lexer;

    public Calculator() {
        super("Calculator");
        state = false;
        value = "";
        JPanel panel = new JPanel(new BorderLayout());
        display = new JTextField(20);
        numberButtons = new JButton[10];
        setAll(numberButtons, i -> new JButton(String.valueOf(i)));
        add = new JButton("+");
        subtract = new JButton("-");
        multiply = new JButton("*");
        divide = new JButton("/");
        pow = new JButton("^");
        equals = new JButton("=");
        decimal = new JButton(".");
        clear = new JButton("C");

        initializeButtonListeners();

        JPanel buttonPanel = new JPanel(new GridLayout(6, 6));
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(pow);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(divide);
        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(multiply);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(subtract);
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(add);
        buttonPanel.add(decimal);
        buttonPanel.add(clear);
        buttonPanel.add(equals);

        panel.add(display, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(250, 250);
        setDefaultLookAndFeelDecorated(true);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeButtonListeners() {
        ActionListener numListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                String buttonText = button.getText();
                if (state) {
                    value = buttonText;
                    state = false;
                } else {
                    value += buttonText;
                }
                display.setText(value);
            }
        };

        ActionListener opListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                String buttonText = button.getText();
                value += buttonText;
                state = false;
                display.setText(value);
            }
        };

        for (int i = 0; i < numberButtons.length; i++) numberButtons[i].addActionListener(numListener);

        add.addActionListener(opListener);
        subtract.addActionListener(opListener);
        multiply.addActionListener(opListener);
        divide.addActionListener(opListener);
        pow.addActionListener(opListener);
        equals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lexer = new Lexer(value);
                Token token;

                tokens = new ArrayList<>();
                while ((token = lexer.getNextToken()).token != TokenType.EOI && token.token != TokenType.Invalid) {
                    tokens.add(token);
                }

                parser = new Parser(tokens);
                ast = parser.parseExpression();
                double result = Interpreter.interpret(ast);
                if (result == (int) result)
                    value = Integer.toString((int) result);
                else
                    value = Double.toString(result);
                display.setText(value);
                state = true;
            }
        });
        decimal.addActionListener(numListener);
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                value = "";
                display.setText("");
            }
        });
    }

    public static void main(String[] args) {
        invokeLater(Calculator::new);
    }
}