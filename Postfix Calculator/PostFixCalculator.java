import java.util.*;
/** Curtis Noonan
 * CS 251 4/2/14
 * Calculates an expression in postfix notation.  
 */

public class PostFixCalculator{
    /** Instantiates all the operator types
    */
    public PostFixCalculator(){
        addOperator("+", new Addition()); 
        addOperator("add", new Addition()); 
        addOperator("-", new Subtraction());
        addOperator("sub", new Subtraction()); 
        addOperator("*", new Multiplication()); 
        addOperator("mult", new Multiplication()); 
        addOperator("/", new Division()); 
        addOperator("div", new Division()); 
        addOperator("=", new Equals()); 
        addOperator("print", new Equals());
    }
    
    private Map<String, Operator> operatorMap = new HashMap<String, Operator>(); 
    
    public Deque<Double> stack = new LinkedList<Double>();
    /** @param String, Operator
    * fills the operatorMap with objects
    */
    public void addOperator(String operator, Operator operation) {
        operatorMap.put(operator, operation);
    }
    /** @param double
    * Pushes a double onto the operand stack
    */
    public void storeOperand(double thingToPush){
        stack.push(thingToPush);
    }
    /** @param String
    * Takes an operator string, looks up the corresponding operator 
    * object in the operator map, pops the appropriate number of operands 
    * (as given by the numArgs method) and places them into a list, evaluates 
    * the operator with the operands in the list, and pushes the result onto 
    * the operand stack
    */
    public void evalOperator(String operator){
        List<Double> operation = new LinkedList<Double>();
        for(int counter = 0; counter < operatorMap.size(); counter++){
            if(operator.equals(operatorMap.get(counter))){
                for(int counterTwo = 0; counterTwo < operatorMap.get(counter).numArgs(); counterTwo++){
                    stack.pop();
                }
                operatorMap.get(counter).eval(operation);
            }
        }
    }
    
    /** Implements Operator interface
    * adds two arguments together
    */
    public class Addition implements Operator {
        /** @return Integer
        */
        public int numArgs() {
            return 2;
        }
        /** @param List<Double>
        * @return double
        */
        public double eval(List<Double> args) {
            return args.get(args.size())+args.get(args.size()-1);
        }        
    }
     /** Implements Operator interface
    * subtracts two arguments together
    */
    public class Subtraction implements Operator {
        /** @return Integer
        */
        public int numArgs() {
            return 2;
        }
        /** @param List<Double>
        * @return double
        */
        public double eval(List<Double> args) {
            return args.get(args.size())-args.get(args.size()-1);
        }        
    }
     /** Implements Operator interface
    * multiplies two arguments together
    */
    public class Multiplication implements Operator {
        /** @return Integer
        */
        public int numArgs() {
            return 2;
        }
        /** @param List<Double>
        * @return double
        */
        public double eval(List<Double> args) {
            return args.get(args.size())*args.get(args.size()-1);
        }        
    }
     /** Implements Operator interface
    * divides two arguments together
    */
    public class Division implements Operator {
        /** @return Integer
        */
        public int numArgs() {
            return 2;
        }
        /** @param List<Double>
        * @return double
        */
        public double eval(List<Double> args) {
            return args.get(args.size())/args.get(args.size()-1);
        }        
    }
     /** Implements Operator interface
    * signals the end of the expression
    */
    public class Equals implements Operator {
        /** @return Integer
        */
        public int numArgs() {
            return 1;
        }
        /** @param List<Double>
        * @return double
        */
        public double eval(List<Double> args) {
            System.out.print(args.get(0));
            return args.get(0);
        }        
    }
    public static void main(String[] args) {
    }
}