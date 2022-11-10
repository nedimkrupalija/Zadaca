package ba.unsa.etf.rpr;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

/**
 * Tests class for evaluate
 * @author Nedim Krupalija
 */


class ExpressionEvaluatorTest {




    /**
     * Tests for function accuracy
     */
    @Test
    void evaluateTest1(){
        assertEquals(101.0,new ExpressionEvaluator().evaluate("( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )"));
    }
    @Test
    void evaluateTest2(){
        assertEquals(1.0,new ExpressionEvaluator().evaluate("( ( ( ( 2 + 4 * 9 ) * ( 6 + 9 * 8 + 6 ) + 6 ) + ( 2 + 4 * 2 ) ) )"));
    }

    /**
     * Few different test cases for exception throwing
     */
    @Test
    void exception1() {
        assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new ExpressionEvaluator().evaluate("( + 8 )");
            }
        });
    }

        @Test
        void exception2() {
            assertThrows(RuntimeException.class, new Executable() {
                @Override
                public void execute() throws Throwable {
                    new ExpressionEvaluator().evaluate("(( 9 + 8 ) )");
                }

            });
        }
            @Test
            void exception3() {
                assertThrows(RuntimeException.class, new Executable() {
                    @Override
                    public void execute() throws Throwable{
                        new ExpressionEvaluator().evaluate("( ( ( 9 + 8 ) )");
                    }
                });
    }
    @Test
    void exception4() {
        assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new ExpressionEvaluator().evaluate("( abc + 8 )");
            }

        });
    }
}