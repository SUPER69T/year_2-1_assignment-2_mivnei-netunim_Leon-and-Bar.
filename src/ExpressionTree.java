import java.util.Stack;

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
            if(!isOperator(token)){ //means the token is an operand.
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
    // Helper method to check if a token is an operator:
    ///*
    /**
     * Checks if a token is an operator.
     *
     * @param token the token to check
     * @return true if the token is an operator, false otherwise
     */
    private boolean isOperator(String token){ //why is this private? were curious..
        return "+-*/^".contains(token);
    }
    //*/
    /**
     * Performs an in-order traversal of the expression tree.
     *
     * @param node the root node of the expression tree
     */
    public void inOrder(Node node){
        //System.out.println("value: " + node.value);
        //System.out.println("left: " + node.left.value);
        //System.out.println("right: " + node.right.value);
        if (node != null){
            if (isOperator(node.value)){
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
            // implement the logic here
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
            if (isOperator(node.value)) {
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

        if (!isOperator(node.value)){
            return Integer.parseInt(node.value);
        }

        // Implement the logic here
        return 7;
    }
    //*/
    public static void main(String[] args){
        ExpressionTree et = new ExpressionTree();
        String postfix = "4 3 7 * + 5 3 4 + / - 6 +";  // Replace with your input

        Node root = et.createExprTreePostfix(postfix);
        ///*
        System.out.print("In-order: ");
        et.inOrder(root);
        System.out.println();
        //*/
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
        System.out.println("Evaluated Result: " + et.evaluateExpression(root));
        */
    }
}
