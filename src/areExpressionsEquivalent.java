import java.util.HashMap;
public class areExpressionsEquivalent{
    ///*
    /**checking whether two trees contain the same values.
     *
     *@param T1_node - the first tree.
     *@param T2_node - the second tree.
     *
     *the singles_holder-map will act like a binary flags-map:
     *it will take elements from both trees and only remove them once the other-tree's-
     *element matches the same valued element from the first tree.
     *
     *@return true if the trees contain the same elements, false if they don't.
     */
    public boolean areExpressionsEquivalent(Node T1_node, Node T2_node) {
        HashMap<Node, Integer> singles_holder;
        public void rec_equivalence(Node T1_node){
            if (T1_node.left != null) { //checking for null-base case.
                if (!ExpressionTree.Operators.isOperator(T1_node.value)) { //checking that the node is an operand.
                    singles_holder.put(T1_node, Integer.valueOf(T1_node.value)); //appending to the map only in case of an operand.
                }
                areExpressionsEquivalent.rec_equivalence(T1_node.left);
                areExpressionsEquivalent.rec_equivalence(T1_node.right);
            }
            //else: returning void.
        }
        rec_equivalence(T1_node);
        return singles_holder.size() == 0;
    }
    //*/
    public static void main(String[] args){
        //for exercise 4:
        //-----
        ExpressionTree et1 = new ExpressionTree();
        String postfix1 = "4 3 7 + + 5 3 4 + + + 6 +";
        Node root1 = et1.createExprTreePostfix(postfix1);
        ExpressionTree et2 = new ExpressionTree();
        String postfix12 = "4 4 7 + + 3 6 5 + + + 3 +";
        Node root2 = et1.createExprTreePostfix(postfix1);
        System.out.print(areExpressionsEquivalent(root1, root2));
        //-----
    }
}
