package ba.unsa.etf.rpr;
import java.util.Scanner;
import java.util.Stack;

/**
 * Java main class
 * @author Nedim Krupalija
 */
public class App {
    public static void main(String[] args) {
        Stack<String> operators = new Stack<String>();
        Stack<Double> values = new Stack<Double>();
        String input = "( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )";

        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        try {
            System.out.println(evaluator.evaluate(input));
        }
        catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }

    }
}