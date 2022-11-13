package ba.unsa.etf.rpr;

import static org.junit.jupiter.api.Assertions.*;

import ba.unsa.etf.rpr.ExpressionEvaluator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

/**
 * Tests class for evaluate
 * @author Nedim Krupalija
 */


class ExpressionEvaluatorTest {

    /**
     * Tests for result accuracy
     */
    @Test
    void evaluateTest1(){
        Assertions.assertEquals(101.0,new ExpressionEvaluator().evaluate("( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )"));
    }

    /**
     * Testing function for real numbers
     */
    @Test
    void evaluateTest2(){
        Assertions.assertEquals(-46.3125, new ExpressionEvaluator().evaluate("( 1.5 + ( ( 25.5 / 2 ) * ( 1.25 - 5 ) ) )"));
    }

    /**
     * Testing sqrt where sqrt is present multiple times
     */
    @Test
    void evaluateTest3(){
        assertEquals(8,Math.round(new ExpressionEvaluator().evaluate("( ( ( 1 + ( ( 5 / 3 ) + sqrt ( 6 ) ) ) / 4 ) * 6 )") ));
    }
    @Test
    void sqrtTest(){
        assertEquals(10.5,new ExpressionEvaluator().evaluate("( sqrt ( 64 ) + ( sqrt ( 25 ) / 2 ) )"));
    }

    /**
     * Testing sqrt where sqrt is on the end
     */
    @Test
    void sqrtTest2(){
        assertEquals(7,Math.round(new ExpressionEvaluator().evaluate(("( 2 + ( 3 + sqrt ( 5 ) ) )"))));
    }

    /**
     * Few different test cases for exception throwing
     */

    /**
     * Checking if there is operand before operator
     */
    @Test
    void exception1() {
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new ExpressionEvaluator().evaluate("( + 8 )");
            }
        });
    }

    /**
     * Checking spacing between operators/operand
     */
    @Test
    void exception2() {
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new ExpressionEvaluator().evaluate("(( 9 + 8 ) )");
            }

        });
    }

    /**
     * Checking for unequal number of brackets
     */
    @Test
    void exception3() {
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable{
                new ExpressionEvaluator().evaluate("( ( ( 9 + 8 ) )");
            }
        });
    }

    /**
     * Checking unknown operatos
     */
    @Test
    void exception4() {
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new ExpressionEvaluator().evaluate("( abc + 8 )");
            }

        });
    }

    /**
     * Checking for multiple operands per set of brackets
     */
    @Test
    void exception5(){
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new ExpressionEvaluator().evaluate("( ( ( ( 2 + 4 * 9 ) * ( 6 + 9 * 8 + 6 ) + 6 ) + ( 2 + 4 * 2 ) ) )");
            }
        });
    }

    /**
     * Exception test for sqrt function
     */
    @Test
    void exception6(){
        Assertions.assertThrows(RuntimeException.class, new Executable(){
            @Override
            public void execute() throws Throwable{
                new ExpressionEvaluator().evaluate("( sqrt ( 5 + 2 ) )");
            }
        });
    }

    @Test
    void exception7(){
        assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new ExpressionEvaluator().evaluate("( 6 )");
            }
        });
    }




}