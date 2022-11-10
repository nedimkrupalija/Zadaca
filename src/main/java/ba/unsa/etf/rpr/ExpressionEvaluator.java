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
 * @version 1.0
 */

public class ExpressionEvaluator {
    private Stack<Double> values;
    private Stack<String> operators;


    /**
     * Function that returns value of given expression using Djikstra's Algorithm for expression evaluation.
     * First, expression is parsed, if it is not real mathematical expression, more than 2 operands per set of brackets,
     * string empty exception is thrown. Function gets to first open parenthesis, ignores it and puts number
     * on stack of values. If current character math. operator it is put on operations stack.
     * When program reaches closed parenthesis if does calculation based on values stored on stack
     * All operators are two operand, except sqrt(only one number allowed in brackets)
     * @param expression
     * @return
     */
    public Double evaluate(String expression){

       Integer numCounter = 0;

        if(!expression.substring(0,1).equals("(")||expression.isEmpty()) throw new RuntimeException("Expression empty or doesn't start with bracket!");
        String[] stringArray = expression.split(" ");
        Integer opened=0, closed=0;
        for(String s : stringArray) {
            if((!operators.isEmpty()&&!operators.peek().equals("sqrt"))&&(operators.size()>values.size()||s.isEmpty())) {
                System.out.println("-----" + operators.peek() + "-----");
                throw new RuntimeException("Input string cannot be parsed!");
            }
            if (s.length() > 1 && !s.equals("sqrt")) {
                try {
                    Double n = Double.parseDouble(s);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Operand is not number or unknown operator!");
                }
            }
            if(s.equals("(")) {
                numCounter = 0;
                opened = opened + 1 ;
            }
            else if(s.equals("+")) operators.push(s);
            else if(s.equals("-")) operators.push(s);
            else if(s.equals("*")) operators.push(s);
            else if(s.equals("/")) operators.push(s);
            else if(s.equals("sqrt")) operators.push(s);
            else if(s.equals(")")){
                closed = closed + 1;
                if(operators.size()==0) continue;
                if((numCounter!=1 && operators.peek().equals("sqrt"))||(numCounter!=2 && !operators.peek().equals("sqrt"))) throw new RuntimeException("More operands than allowed!");
                values.push(findValue(operators,values));
            }
            else{
                values.push(Double.parseDouble(s));
                numCounter = numCounter + 1;
            }
        }
        if(opened!=closed) throw new RuntimeException("Not equal number of opened and closed brackets!");
        while(operators.size()!=0) values.push(findValue(operators,values));
        return values.pop();
    }

    /**
     * Help function for return inc, dec, mul, od div value depending on current operator and value
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
     * Empty constructor that makes empty values and operators stacks
     */
    public ExpressionEvaluator() {
        this.values = new Stack<Double>();
        this.operators = new Stack<String>();
    }

}
