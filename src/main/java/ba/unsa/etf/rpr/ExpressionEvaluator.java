


package ba.unsa.etf.rpr;
import org.jetbrains.annotations.NotNull;
import java.util.Stack;

/**
 * Class for parsing and evaluating expressions using Djikstra
 * algorithm.
 * @author Nedim Krupalija
 * @version 1.0
 */

public class ExpressionEvaluator {
    private Stack<Double> values;
    private Stack<String> operators;

    /**
     * Function that returns value of given expression using Djikstra's Algorithm for expression evaluation.
     * First, expression is parsed, if it is not real mathematical expression
     * exception is thrown. Function gets to first open parenthesis, ignores it and puts number
     * on stack of values. If current character is for math. operation it is put on operations stack.
     * When program reaches closed parenthesis if does calculation based on values stored on stack
     * @param expression
     * @return
     */
    public Double evaluate(String expression){
        if(!expression.substring(0,1).equals("(")||expression.isEmpty()) throw new RuntimeException("Input string cannot be parsed!");
        String[] stringArray = expression.split(" ");
        Integer opened=0, closed=0;
        for(String s : stringArray) {
            if(operators.size()>values.size()||s.isEmpty()) throw new RuntimeException("Input string cannot be parsed!");
            if (s.length() > 1) {
                try {
                    Integer n = Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Input string cannot be parsed!");
                }
            }
            if(s.equals("(")) opened = opened + 1 ;
            else if(s.equals("+")) operators.push(s);
            else if(s.equals("-")) operators.push(s);
            else if(s.equals("*")) operators.push(s);
            else if(s.equals("/")) operators.push(s);
            else if(s.equals(")")){
                closed = closed + 1;
                if(operators.size()==0) continue;

                values.push(findValue(operators,values));
            }
            else values.push(Double.parseDouble(s));
        }
        if(opened!=closed) throw new RuntimeException("Not equal number of opened and closed parenthesis!");
        while(operators.size()!=0) values.push(findValue(operators,values));
        return values.pop();
    }


    private Double findValue(Stack<String> operators, Stack<Double> values){
        String op = operators.pop();
        Double val = values.pop();
        if(op.equals("+")) val = values.pop() + val;
        else if(op.equals("-")) val = values.pop() - val;
        else if(op.equals("*")) val = values.pop() * val;
        else if(op.equals("/")) val = values.pop() / val;
        return val;
    }

    /**
     *
     * @param values
     * @param operators
     * Construtor that takes initializes values and operators with given parameters
     */
    public ExpressionEvaluator(Stack<Double> values, Stack<String> operators) {
        this.values = new Stack<Double>();
        this.values = values;
        this.operators = new Stack<String>();
        this.operators = operators;
    }

    /**
     * Empty constructor that makes empty values and operators stacks
     */
    public ExpressionEvaluator() {
        this.values = new Stack<Double>();
        this.operators = new Stack<String>();
    }

}
