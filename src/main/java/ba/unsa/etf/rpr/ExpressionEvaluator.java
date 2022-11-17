package ba.unsa.etf.rpr;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Class for parsing and evaluating expressions using Djikstra
 * algorithm.
 * We assume that all expressions are in brackets, and there is no support for operator priority
 * @author Nedim Krupalija
 * @version 1.3
 */

public class ExpressionEvaluator {
    private Stack<Double> values;
    private Stack<String> operators;

    /**
     * checks if char c is number or point
     * @param c
     * @return
     */

    private boolean isNumber(char c){
        return((c>='0'&&c<='9')||c=='.');
    }

    /**
     * checks if char c is operator +,*,/,-
     * @param c
     * @return
     */
    private boolean isOperator(char c){
        return (c=='+'||c=='-'||c=='*'||c=='/');
    }

    /**
     * Checks if given string is operator +,*,/,-
     * @param s
     * @return
     */
    private boolean isStringOperator(String s){
        return (s.equals("+")||s.equals("-")||s.equals("/")||s.equals("*"));
    }

    /**
     * Function that returns value of given expression using Djikstra's Algorithm for expression evaluation.
     * First, expression is parsed, if it is not real mathematical expression, more than 2 operands per set of brackets,
     * string empty exception is thrown. Function gets to first open parenthesis, ignores it and puts number
     * on stack of values. If current character math. operator it is put on operations stack.
     * When program reaches closed parenthesis if does calculation based on values stored on stack
     * All operators are two operand, except sqrt(only one number allowed in brackets). Brackets without operators are
     * not allowed, in other words more brackets than necessary.
     * @param expression
     * @return
     */

    public Double evaluate(String expression){
       Integer numCounter = 0, sqrtCounter = 0, operatorCounter = 0;
        boolean wasSqrt=false;
        boolean wasOperator = false;
        if(expression.isEmpty())  throw new RuntimeException("Expression empty!");
        if(!expression.substring(0,1).equals("(")) throw new RuntimeException("Expression doesn't start with bracket!");
        String[] stringArray = expression.split(" ");
        Integer opened=0, closed=0;
        for(int i=0;i<stringArray.length;i++) {
            if((!operators.isEmpty()&&!operators.peek().equals("sqrt"))&&(operators.size()>values.size()||stringArray[i].isEmpty())) {
                throw new RuntimeException("Input string cannot be parsed!");
            }
            if (stringArray[i].length() > 1 && !stringArray[i].equals("sqrt")) {
                try {
                    Double n = Double.parseDouble(stringArray[i]);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Operand is not number or known operator!");
                }
            }
            if(stringArray[i].equals("(")) {
                opened = opened + 1 ;

            }
            else if(isStringOperator(stringArray[i])){
                if(wasSqrt) throw new RuntimeException("More operands than allowed!");
                operatorCounter = operatorCounter + 1;
                operators.push(stringArray[i]);
            }
            else if(stringArray[i].equals("sqrt")) {
                operators.push(stringArray[i]);
                operatorCounter = operatorCounter + 1;
                sqrtCounter = sqrtCounter + 1;
                wasSqrt = true;
            }
            else if(stringArray[i].equals(")")){
                closed = closed + 1;
                if(operators.size()==0) continue;
                if(numCounter-1!=operatorCounter) throw new RuntimeException("More operands than allowed!");
                values.push(findValue(operators,values));
                wasSqrt = false;
            }
            else{
                values.push(Double.parseDouble(stringArray[i]));
                if(wasSqrt)  numCounter = numCounter + 2;
                else numCounter = numCounter + 1;
            }
        }
        if(operatorCounter!=closed) throw new RuntimeException("More operands than allowed!");
        if(opened!=closed) throw new RuntimeException("Not equal number of opened and closed brackets!");
        while(operators.size()!=0) values.push(findValue(operators,values));
        return values.pop();
    }

    /**
     * Second function that does same calculation. Difference is that for this function there is no guarantee
     * that input expression will have spaces between each character (it can or can't have them)
     * Implemented by iterating throw char array and parsing numbers, operators etc.
     * @param expression
     * @return
     */
    public Double evaluateNoSpaces(String expression){
        char nextChar=' ';
        Integer operatorCounter = 0,numCounter = 0, opened = 0, closed = 0, sqrtCounter = 0;
        boolean wasSqrt = false;
        int j = 0;
        if(expression.length()==0) throw new RuntimeException("Expression empty!");
        char[] charArray = expression.toCharArray();
        String array = "";
        if(expression.charAt(0)!='(') throw new RuntimeException("Expression must start with ( !");
        for(int i=0;i<charArray.length;){
            if(charArray[i]==32)
            {
                i++;
                continue;
            }
            if(charArray[i]=='(') {
                opened = opened + 1;

            }
            else if(charArray[i]=='s'){
                String sqrtCheck = "";
                while((charArray[i]=='s'||charArray[i]=='q'||charArray[i]=='r'||charArray[i]=='t')&&(i<charArray.length)){
                    sqrtCheck = sqrtCheck + Character.toString(charArray[i]);
                    i++;
                }
                i--;
                if(sqrtCheck.equals("sqrt")) {
                    operators.push(sqrtCheck);
                    wasSqrt = true;
                    sqrtCounter = sqrtCounter + 1;
                    operatorCounter = operatorCounter + 1;
                }
            }
            else if(isOperator(charArray[i])) {
                if(wasSqrt) throw new RuntimeException("More operands than allowed!");
                operators.push(Character.toString(charArray[i]));
                operatorCounter = operatorCounter + 1;
            }
            else if(charArray[i]==')'){
                closed = closed + 1;
                if(operators.size()==0) {
                    i++;
                    continue;
                }
                if(numCounter-1!=operatorCounter) throw new RuntimeException("More operands than allowed!");
                values.push(findValue(operators,values));
                wasSqrt = false;
            }
            else{
                String num = "";
                if(!isNumber(charArray[i])) throw new NumberFormatException("Not allowed operator/operand!");
                while(isNumber(charArray[i])){
                    num = num + Character.toString(charArray[i]);
                    i++;
                }

                i--;
                if(!num.isEmpty()){
                    values.push(Double.parseDouble(num));
                    if(wasSqrt) numCounter = numCounter + 2;
                    else numCounter = numCounter + 1;

                }
            }
            i++;
        }
        if(operatorCounter!=closed) throw new RuntimeException("More operands than allowed!");
        if(opened!=closed) throw new RuntimeException("Not equal number of opened and closed brackets!");
        while(operators.size()!=0) values.push(findValue(operators,values));
        return values.pop();
    }


    /**
     * Help function that returns operator(value)
     * @param operators
     * @param values
     * @return
     */
    private Double findValue(Stack<String> operators, Stack<Double> values){
        String op = operators.pop();
        Double val = values.pop();
        if(op.equals("+")) val = values.pop() + val;
        else if(op.equals("-")) val = values.pop() - val;
        else if(op.equals("*")) val = values.pop() * val;
        else if(op.equals("/")) val = values.pop() / val;
        else if(op.equals("sqrt")) val = Math.sqrt(val);
        return val;
    }


    /**
     * Constructor for initialization of stacks
     */
    public ExpressionEvaluator() {
        this.values = new Stack<Double>();
        this.operators = new Stack<String>();
    }

}
