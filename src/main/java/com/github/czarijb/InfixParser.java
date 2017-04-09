package com.github.czarijb;

import java.text.ParseException;
import static com.github.czarijb.ErrorType.*;
import static com.github.czarijb.TokenType.*;

public class InfixParser {
    //  The token that defines the end of the expression
    final static String EOFEXPRES = "\0";

    // Variables
    private TokenType tokType;
    private String expression;
    private int explds;
    private String token;

    public String toString() {
        return String.format("Exp = {0}\nexplds = {1}\nTokenType = {2}\nTokType = {3}", expression.toString(), explds,
                token.toString(), tokType);
    }

    //  Get next token
    private void getToken() {
        tokType = NONE;
        token = "";

        //  check ends of expression
        if (explds == expression.length()) {
            token = EOFEXPRES;
            return;
        }

        //  check spaces, if have - ignore it.
        while (explds < expression.length() && Character.isWhitespace(expression.charAt(explds)))
            ++explds;

        //  check ends of expression
        if (explds == expression.length()) {
            token = EOFEXPRES;
            return;
        }

        if (isDelim(expression.charAt(explds))) {
            token += expression.charAt(explds);
            explds++;
            tokType = DELIMITER;
        } else if (Character.isLetter(expression.charAt(explds))) {
            while (!isDelim(expression.charAt(explds))) {
                token += expression.charAt(explds);
                explds++;
                if (explds >= expression.length())
                    return;
            }
            tokType = VARIABLE;
        } else if (Character.isDigit(expression.charAt(explds))) {
            while (!isDelim(expression.charAt(explds))) {
                token += expression.charAt(explds);
                explds++;
                if (explds >= expression.length()) {
                    return;
                }
            }
            tokType = NUMBER;
        } else {
            token = EOFEXPRES;
            return;
        }
    }

    private boolean isDelim(char charAt) {
        if ((" +-/*=".indexOf(charAt)) != -1)
            return true;
        return false;
    }

    //  entry point analyzer
    public double evaluate(String expstr) throws ParseException {
        double result;
        expression = expstr;
        explds = 0;
        getToken();

        if (token.equals(EOFEXPRES))
            handleErr(NOEXP);   //  No expression

        //  analyze and calculation expression
        result = evalExp2();

        if (!token.equals(EOFEXPRES))
            handleErr(SYNTAXERROR);

        return result;
    }

    //  Addition or subtraction
    private double evalExp2() throws ParseException {
        char op;
        double result;
        double partialResult;
        result = evalExp3();

        while ((op = token.charAt(0)) == '+' ||
                op == '-') {
            getToken();
            partialResult = evalExp3();
            switch (op) {
                case '-':
                    result -= partialResult;
                    break;
                case '+':
                    result += partialResult;
                    break;
                default:
                     break;
            }
        }
        return result;
    }

    //  Multiplication or division
    private double evalExp3() throws ParseException {
        char op;
        double result;
        double partialResult;

        result = evalExp4();
        while ((op = token.charAt(0)) == '*' || op == '/'){
            getToken();
            partialResult = evalExp4();
            switch (op) {
                case '*':
                    result *= partialResult;
                    break;
                case '/':
                    if (partialResult == 0.0)
                        handleErr(DIVBYZERO);
                    result /= partialResult;
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    //  To define a unary + or -
    private double evalExp4() throws ParseException {
        double result;

        String op;
        op = " ";

        if ((tokType == DELIMITER) && token.equals("+") ||
                token.equals("-")) {
            op = token;
            getToken();
        }
        result = atom();
        return result;
    }

    //  Get value of the number
    private double atom() throws ParseException {
        double result = 0.0;

        switch (tokType) {
            case NUMBER:
                try {
                    result = Double.parseDouble(token);
                } catch (NumberFormatException exc) {
                    handleErr(SYNTAXERROR);
                }
                getToken();
                break;
            default:
                handleErr(SYNTAXERROR);
                break;
        }
        return result;
    }

    //  Throw exception
    private void handleErr(ErrorType errorType) throws ParseException {
        switch (errorType){

            case SYNTAXERROR:
                throw new ParseException("Syntax error", explds);

            case NOEXP:
                throw new ParseException("No Expression Present", explds);

            case DIVBYZERO:
                throw new ParseException("Division by zero", explds);
        }
    }
}

