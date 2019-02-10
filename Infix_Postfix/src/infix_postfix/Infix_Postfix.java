/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infix_postfix;

import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Abdullah Shublaq
 */

public class Infix_Postfix {

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')';
    }

    private static boolean isLowerPrecedence(char operatorOldChar, char operatorNewChar) {
        boolean high = true;//true if old char is higher than new char
        int operatorOld = 0, operatorNew = 0;

        switch (operatorOldChar) {
            case '-':
                operatorOld = 1;
                break;
            case '+':
                operatorOld = 2;
                break;
            case '/':
                operatorOld = 3;
                break;
            case '*':
                operatorOld = 4;
                break;
            default:
                break;
        }

        switch (operatorNewChar) {
            case '-':
                operatorNew = 1;
                break;
            case '+':
                operatorNew = 2;
                break;
            case '/':
                operatorNew = 3;
                break;
            case '*':
                operatorNew = 4;
                break;
            default:
                break;
        }

        if (operatorNewChar == '(') {
            high = false;
        } else if (operatorNewChar == ')') {
            high = true;
        } else if (operatorOld < operatorNew) {
            high = false;
        } else if (operatorOld >= operatorNew) {
            high = true;
        }

        return high;
    }

    public static int postfixResult(String PostFix) {
        Stack<Integer> s = new Stack<>();
        Scanner in = new Scanner(PostFix);

        while (in.hasNext()) {
            if (in.hasNextInt()) {
                s.push(in.nextInt());
            } else {
                int num2 = s.pop();
                int num1 = s.pop();
                String op = in.next();

                switch (op) {
                    case "+":
                        s.push(num1 + num2);
                        break;
                    case "-":
                        s.push(num1 - num2);
                        break;
                    case "*":
                        s.push(num1 * num2);
                        break;
                    case "/":
                        s.push(num1 / num2);
                        break;
                    default:
                        break;
                }
            }
        }
        return s.pop();
    }

    public static void Infix_TO_PostFix(String Infix) {

        StringBuilder PostFix = new StringBuilder();
        Stack<Character> stack = new Stack();
        boolean TwoNumber = false;
        char current;

        for (int i = 0; i < Infix.length(); i++) {
            current = Infix.charAt(i);
            if (isOperator(current)) {
                if (current == ')') {
                    while (!stack.isEmpty()) {
                        if (stack.peek() == '(') {
                            break;
                        }
                        PostFix.append(" ");
                        PostFix.append(stack.pop());
                    }
                    if (!stack.isEmpty()) {//pop ')'
                        stack.pop();
                    }
                } else if (!stack.empty()) {
//                    if (!isLowerPrecedence(stack.peek(), current)) {
//                        PostFix.append(" ");
//                        PostFix.append(stack.pop());
//                        stack.push(current);
//                    } else {
//                        stack.push(current);
//                    }
                    if (!isLowerPrecedence(stack.peek(), current)) {
                        stack.push(current);
                    } else {
//                        while (!stack.isEmpty()) {
//                            PostFix.append(" ");
//                            PostFix.append(stack.pop());
//                        }
                        PostFix.append(" ");
                        PostFix.append(stack.pop());
                        stack.push(current);
                    }
                } else if (stack.empty()) {
                    stack.push(current);
                }
                TwoNumber = false;
            } else if (!isOperator(current) && current != ' ') {
                if (TwoNumber) {
                    PostFix.append(current);
                } else {
                    PostFix.append(" ");
                    PostFix.append(current);
                }
                TwoNumber = true;
            } else {//current = ' ' 
                TwoNumber = false;
            }
        }
        while (!stack.empty()) {
            PostFix.append(" ");
            PostFix.append(stack.pop());
        }
        System.out.println(PostFix.toString() + "  =  " + postfixResult(PostFix.toString()));
    }

    public static void Postfix_TO_Infex(String Postfix) {
        String InFix = "";
        Stack<String> stack = new Stack();
        String currentSTR;
        char currentCHR;
        String num1;
        String num2;
        char Precedence = ' ';
        String High = "";
        int setNum = 0;
        String cut;
        int result;

        result = postfixResult(Postfix);

        for (int i = 0; i < Postfix.length(); i++) {
            currentSTR = Postfix.charAt(i) + "";
            currentCHR = Postfix.charAt(i);
            if (!isOperator(currentCHR) && currentCHR != ' ') {
                stack.push(currentSTR);
            } else if (isOperator(currentCHR)) {
                if (!stack.empty() && Precedence != ' ' && !isLowerPrecedence(Precedence, currentCHR)) {
                    for (int j = stack.size() - 1; j >= 0; j--) {
                        cut = stack.get(j);
                        if (cut.length() > 1) {
                            High = stack.get(j);
//                            stack.remove(j);
                            setNum = j;
                            break;
                        }
                    }
                    InFix = "(" + High + ")";
                    stack.set(setNum, InFix);
                    InFix = "";
                }
                num2 = stack.pop();
                num1 = stack.pop();

                Precedence = currentCHR;
                InFix = InFix + num1;
                InFix = InFix + currentSTR;
                InFix = InFix + num2;
                stack.push(InFix);
                InFix = "";
            }
        }
        InFix = stack.pop();
        System.out.println(InFix + "   =  " + result);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("The Operators is : * / + -");
        System.out.print("Choices:\n  1.Infix to Postfix.\n  2.Postfix to Infix.\n\tYour Cohice : ");
        int Choice = in.nextInt();
        in.nextLine();
        switch (Choice) {
            case 1:
                System.out.println("------------------------");
                System.out.print("Enter the Infix : ");
                String Infix = in.nextLine();
                Infix_TO_PostFix(Infix);
                break;
            case 2:
                System.out.println("------------------------");
                System.out.print("Enter the Postfix : ");
                String Postfix = in.nextLine();
//                in.next();
                Postfix_TO_Infex(Postfix);
                break;
            default:
                System.err.println("Error Code!");
        }
    }

}
