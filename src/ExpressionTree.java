import java.util.HashMap;
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

    Operators OP = new Operators(); //gonna be useful for later isOperator() checks.
    public Node createExprTreePostfix(String postfix){
        Stack<Node> stack = new Stack<>();
        String[] tokens = postfix.trim().split("\\s+");
        for(String token : tokens){
            //stack.forEach(t->System.out.print(t.value));
            //System.out.println();
            if(!OP.isOperator(token)){ //means the token is an operand.
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
        private final Map<String, BiFunction<Integer, Integer, Integer>> OPS =
                Map.of(
                "+", (a,b) -> a + b,
                "-", (a,b) -> a - b,
                "*", (a,b) -> a * b,
                "/",(a,b) -> a / b,
                "^",(a,b) -> (Integer)(int)Math.pow(a, b)); //...that's weird...
        //-------
        /**
         * Returns the operation function.
         *
         * @return the operation function itself as a lambda function.
         * @throws IllegalArgumentException - if - op is non-existent in the - OPS-Map.
         */
        public BiFunction<Integer, Integer, Integer> get(String op){ //also possible to do with the IntBinaryOperator interface, as it -
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
        public boolean isOperator(String s){ //change from static.
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
        if(node != null){
            if(OP.isOperator(node.value)){
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
            if (OP.isOperator(node.value)) {
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
    public int evaluateExpression(Node node) {
        if (node == null) return 0;

        if (!OP.isOperator(node.value)){ //the node is an operand:
            return Integer.parseInt(node.value);
        }
        //the node is an operator:
        int left = evaluateExpression(node.left);
        int right = evaluateExpression(node.right);
        int result = 0;
        try {
            result = OP.get(node.value).apply(left, right); //might throw - NullPointerException if a division by 0 occurs.
        }
        catch (NullPointerException e) {System.out.print(e.getMessage());}
        return result;
    }
    ///*
    /**
     *
     *
     */
    public boolean areExpressionsEquivalent(Node T1_node, Node T2_node){
        //checking - 3 null cases:
        if(T1_node.value == null && T2_node.value == null) return true;
        else if(T1_node.value == null || T2_node.value == null) return false;
        //
        HashMap<Node, Integer> singles_holder = new HashMap<>();
        System.out.println("singles_holder: " + singles_holder);
        rec_equivalence(T1_node, true, singles_holder); //true = T1_node.
        rec_equivalence(T2_node, false, singles_holder); //false = T2_node.
        return singles_holder.size() == 0;
    }
    //*/
    ///*
    /**An accommodating method for areExpressionsEquivalent.
     *
     *@Params: node - T1/T2 roots and their child-nodes.
     *
     *@param: B - A boolean variable, telling which tree is currently running: T1/T2.
     *
     *@param: singles_holder - the map from areExpressionsEquivalent, being modified.
     *
     * @Returns: void.
     */
    public void rec_equivalence(Node node, Boolean B, HashMap<Node, Integer> singles_holder){
        if (node != null){ //checking for null-base case.
            if(!OP.isOperator(node.value)){ //checking that the node is an operand.
                if(B){ //boolean variable.
                    singles_holder.put(node, Integer.parseInt(node.value)); //appending to the map only in case of an operand of the T1_node.
                }else{
                    singles_holder.remove(node, Integer.parseInt(node.value)); //removing from the map only in case of an operand of the T2_node.
                }
            }
            if(node.left != null) rec_equivalence(node.left, B, singles_holder);
            if(node.right != null) rec_equivalence(node.right, B, singles_holder);
        }
        //else: returning void.
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
        //for exercise 4:
        //-----
        ExpressionTree et1 = new ExpressionTree();
        String postfix1 = "4 3 7 + + 5 3 4 + + + 6 +";
        Node root1 = et1.createExprTreePostfix(postfix1);
        ExpressionTree et2 = new ExpressionTree();
        String postfix2 = "4 4 7 + + 3 6 5 + + + 3 +";
        Node root2 = et2.createExprTreePostfix(postfix2);
        ExpressionTree Test = new ExpressionTree();
        System.out.print(Test.areExpressionsEquivalent(root1, root2));
        //-----
    }
}