import jdk.dynalink.Operation;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiFunction;

///*
class Node{
    String value;
    Node left, right;

    Node(String value){
        this.value = value;
        this.left = this.right = null;
    }
}
//*/

public class ExpressionTree{
    ///*
    /**
     * Creates an expression tree from a postfix expression.
     *
     * @param postfix the postfix expression
     * @return the root node of the expression tree
     */

    public Node createExprTreePostfix(String postfix){
        Stack<Node> stack = new Stack<>();
        String[] tokens = postfix.trim().split("\\s+");
        for(String token : tokens){
            //stack.forEach(t->System.out.print(t.value));
            //System.out.println();
            if(!Operators.isOperator(token)){ //means the token is an operand.
                Node n = new Node(token); //constructing a new Node.
                stack.push(n);
            }
            else{
                Node n = new Node(token); //constructing a new Node.
                n.right = stack.pop();
                n.left = stack.pop();
                stack.push(n);
            }
        }
        return stack.pop();
    }
    //*/
    ///*
    public final class Operators{ //a general class for checking and getting the operators.
        //the Map that holds the operators as keys and their lambda functions as the values:
        //-------
        private static final Map<String, BiFunction<Integer, Integer, Integer>> OPS =
                Map.of(
                "+", (a,b) -> a + b,
                "-", (a,b) -> a - b,
                "*", (a,b) -> a * b,
                "/",(a,b) -> a / b,
                "^",(a,b) -> a ^ b);
        //-------
        /**
         * Returns the operation function.
         *
         * @return the operation function itself as a lambda function.
         * @throws IllegalArgumentException - if - op is non existant in the - OPS-Map.
         */
        public static BiFunction<Integer, Integer, Integer> get(String op){ //also possible to do with the IntBinaryOperator interface, as it -
            //prevents boxing of primitive operators(int) in case of only using int-types but constantly wrapping then in the Integer wrapper-class.
            var f = OPS.get(op);
            if (f == null) {
                throw new IllegalArgumentException("Unknown operator: " + op);
            }
            return f;
        }
        /**
         * Checks if a string is an operator contained within the operators map.
         *
         * @param s the string to check.
         * @return true if the string is an operator, false otherwise.
         */
        public static boolean isOperator(String s){
            return OPS.containsKey(s);
        }
    }
    //*/
    ///*
    /**
     * Performs an in-order traversal of the expression tree.
     *
     * @param node the root node of the expression tree
     */
    public void inOrder(Node node){
        if (node != null){
            if (Operators.isOperator(node.value)){
                System.out.print("( ");
            }
            if(node.left == null && node.right == null) {
                System.out.print(node.value + " "); //printing a leaf operand or an operator itself.
            }
            if(node.left != null){ //checking left tree.
                inOrder(node.left);
                System.out.print(node.value);
                System.out.print(" ");
            }
            if (node.right != null){ //checking right tree.
                inOrder(node.right);
                System.out.print(") ");
            }
        }
    }
    //*/
    ///*
    /**
     * Performs a pre-order traversal of the expression tree.
     *
     * @param node the root node of the expression tree
     */
    public void preOrder(Node node){
        if (node != null){
            System.out.print(node.value + " ");
            if(node.left != null) {
                preOrder(node.left);
            }
            if(node.right != null) {
                preOrder(node.right);
            }
        }
    }
    //*/
    ///*
    /**
     * Performs a post-order traversal of the expression tree.
     *
     * @param node the root node of the expression tree
     */
    public void postOrder(Node node){
        if (node != null){
            if (Operators.isOperator(node.value)) {
                if (node.left == null && node.right == null) System.out.print(node.value);
                else {
                    postOrder(node.left);
                    postOrder(node.right);
                }
            }
            System.out.print(node.value + " ");
        }
        //else returns with void value.
    }
    //*/
    ///*
    /**
     * Evaluates the expression tree.
     *
     * @param node the root node of the expression tree
     * @return the evaluated result
     * @throws ArithmeticException if division by zero occurs
     * @throws IllegalArgumentException if an invalid operator is encountered or if operands are missing
     */
    public int evaluateExpression(Node node){
        if (node == null) return 0;

        if (!Operators.isOperator(node.value)){ //the node is an operand:
            return Integer.parseInt(node.value);
        }
        //the node is an operator:
        int left = evaluateExpression(node.left);
        int right = evaluateExpression(node.right);
        return Operators.get(node.value).apply(left, right);
    }
    //*/
    public static void main(String[] args){
        ExpressionTree et = new ExpressionTree();
        String postfix = "4 3 7 * + 5 3 4 + / - 6 +";  // Replace with your input

        Node root = et.createExprTreePostfix(postfix);
        /*
        System.out.print("In-order: ");
        et.inOrder(root);
        System.out.println();
        */
        /*
        System.out.print("Pre-order: ");
        et.preOrder(root);
        System.out.println();
        */
        /*
        System.out.print("Post-order: ");
        et.postOrder(root);
        System.out.println();
        */
        /*
        System.out.println("Evaluated Result: " + et.evaluateExpression(root) + ".");
        */
    }
}
