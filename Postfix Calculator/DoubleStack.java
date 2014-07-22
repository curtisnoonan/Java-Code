import java.util.*;
/** Curtis Noonan
 * CS 251 4/2/14
 * Implements an interface of a stack of doubles.  
 */

public class DoubleStack implements Stack<Double>{

    public Deque<Double> stack = new LinkedList<Double>();
    /** @return boolean
    * return true if the stack is empty
    */
    public boolean isEmpty(){
        if(stack.size() == 0){
            return true;
        }
        else{
            return false;
        }
    }
    /** @param Double
    * pushes a double onto the stack
    */
    public void push(Double val){
        stack.push(val);
    }
    /** @param Double
    * pops a double off the stack
    */
    public Double pop(){
        return stack.pop();
    }
    /** @param Double
    * peeks at a double on the stack
    */
    public Double peek(){
        return stack.peek();
    }
}