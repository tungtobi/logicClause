package uet.fit.ai.core.expr;

import java.io.*;

public class Lexer {
    private StreamTokenizer input;

    private static final int EOL = -3;
    private static final int EOF = -2;
    private static final int INVALID = -1;

    public static final int OR = 1;
    public static final int AND = 2;
    public static final int NOT = 3;
    public static final int IMPLIES = 4;

    public static final int VARIABLE = 5;

    public static final int LEFT = 6;
    private static final int RIGHT = 7;

    private char currentVariable;

    public Lexer(InputStream in) {
        Reader r = new BufferedReader(new InputStreamReader(in));
        input = new StreamTokenizer(r);

        input.resetSyntax();
        input.wordChars('a', 'z');
//        input.wordChars('A', 'Z');
        input.whitespaceChars('\u0000', ' ');
        input.whitespaceChars('\n', '\t');

        input.ordinaryChar('(');
        input.ordinaryChar(')');
        input.ordinaryChar('&');
        input.ordinaryChar('|');
        input.ordinaryChar('!');
        input.ordinaryChar('-');
        input.ordinaryChar('>');
    }

    public int nextSymbol() {
        int symbol;

        try {
            switch (input.nextToken()) {
                case StreamTokenizer.TT_EOL:
                    symbol = EOL;
                    break;
                case StreamTokenizer.TT_EOF:
                    symbol = EOF;
                    break;
                case StreamTokenizer.TT_WORD: {
                    if (input.sval.length() == 1) {
                        symbol = VARIABLE;
                        currentVariable = input.sval.charAt(0);
                    } else
                        symbol = INVALID;
                    break;
                }
                case '(':
                    symbol = LEFT;
                    break;
                case ')':
                    symbol = RIGHT;
                    break;
                case '&':
                    symbol = AND;
                    break;
                case '|':
                    symbol = OR;
                    break;
                case '!':
                    symbol = NOT;
                    break;
                case '-':
                    if (input.nextToken() == '>')
                        symbol = IMPLIES;
                    else
                        symbol = INVALID;
                    break;
                default:
                    symbol = INVALID;
            }
        } catch (IOException e) {
            symbol = EOF;
        }

        return symbol;
    }

    public char getVariable() {
        return currentVariable;
    }

    @Override
    public String toString() {
        return input.toString();
    }
}