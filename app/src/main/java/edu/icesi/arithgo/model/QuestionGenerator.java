package edu.icesi.arithgo.model;

import java.util.Random;

public class QuestionGenerator {

    private static final String[] operators = {"+", "-", "/", "*"};

    private int operand1;
    private String operator;
    private int operand2;
    private int result;

    public String generateNewQuestion(){

        operand1 = (int) (Math.random()*1000);
        operand2 = (int) (Math.random()*1000);
        operator = operators[(int) (Math.random()*4)];

        switch (operator){
            case "+":
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "*":
                result = operand1 * operand2;
                break;
            case "/":
                result = operand1 / operand2;
                break;
        }

        String question = operand1 + " " + operator + " " + operand2;

        return question;
    }

    public int getOperand1() {
        return operand1;
    }

    public String getOperator() {
        return operator;
    }

    public int getOperand2() {
        return operand2;
    }

    public int getResult() {
        return result;
    }
}
