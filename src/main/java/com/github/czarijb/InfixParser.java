package com.github.czarijb;


import static com.github.czarijb.ErrorType.DIVBYZERO;
import static com.github.czarijb.ErrorType.NOEXP;
import static com.github.czarijb.ErrorType.SYNTAXERROR;
import static com.github.czarijb.TokenType.*;

public class InfixParser {

    private TokenType tokType;

    //  The token that defines the end of the expression
    final String EOFEXPRES = "\0";

    // Variables
    private String expression;
    private int explds;
    private String token;



    public String toString() {
        return String.format("Exp = {0}\nexplds = {1}\nTokenType = {2}\nTokType = {3}", expression.toString(), explds,
                token.toString(), tokType);
    }

    //  Получить следующую лексему
    private void getToken() {
        tokType = NONE;
        token = "";

        //  Проверка на окончание выражения
        if (explds == expression.length()) {
            token = EOFEXPRES;
            return;
        }
        //  Проверка на пробелы, если есть пробел - игнорируем его.
        while (explds < expression.length() && Character.isWhitespace(expression.charAt(explds)))
            ++explds;
        //  Проверка на окончание выражения
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
                    break;
            }
            tokType = VARIABLE;
        } else if (Character.isDigit(expression.charAt(explds))) {
            while (!isDelim(expression.charAt(explds))) {
                token += expression.charAt(explds);
                explds++;
                if (explds >= expression.length())
                    break;
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

    //  Точка входа анализатора
    public double evaluate(String expstr) throws ParserException {

        double result;

        expression = expstr;
        explds = 0;
        getToken();

        if (token.equals(EOFEXPRES))
            handleErr(NOEXP);   //  Нет выражения

        //  Анализ и вычисление выражения
        result = evalExp2();

        if (!token.equals(EOFEXPRES))
            handleErr(SYNTAXERROR);

        return result;
    }

    //  Сложить или вычислить два терма
    private double evalExp2() throws ParserException {

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
            }
        }
        return result;
    }

    //  Умножить или разделить
    private double evalExp3() throws ParserException {

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
            }
        }
        return result;
    }

    //  Определить унарные + или -
    private double evalExp4() throws ParserException {
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

    //  Получить значение числа
    private double atom() throws ParserException {

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

    //  Кинуть ошибку
    private void handleErr(ErrorType errorType) throws ParserException {

        switch (errorType){

            case SYNTAXERROR:
                throw new ParserException("Syntax error");

            case NOEXP:
                throw new ParserException("No Expression Present");

            case DIVBYZERO:
                throw new ParserException("Division by zero");
        }
    }
}

