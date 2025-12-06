import java.util.HashMap;
public class exercise_4 {
    ///*
    /**
     * checking whether two trees contain the same values.
     *
     * @param T1_node - the first tree.
     * @param T2_node - the second tree.
     *                <p>
     *                the singles_holder-map will act like a binary flags-map:
     *                it will take elements from both trees and only remove them once the other-tree's-
     *                element matches the same valued element from the first tree.
     * @return true if the trees contain the same elements, false if they don't.
     */
    public boolean areExpressionsEquivalent(Node T1_node, Node T2_node) {
        HashMap<Integer, Node> singles_holder = null;
        class nested_equivalence_class {
            public void rec_equivalence(Node node, Boolean T, HashMap<Integer, Node> singles_holder) {
                if (node.left != null) { //checking for null-base case.
                    if (!ExpressionTree.Operators.isOperator(node.value)) { //checking that the node is an operand.
                        if (T) {//
                            singles_holder.put(Integer.valueOf(node.value), node); //appending to the map only in case of an operand of the T1_node.
                        } else {
                            singles_holder.remove(Integer.valueOf(node.value), node); //removing from the map only in case of an operand of the T2_node.
                        }
                    }
                    rec_equivalence(node.left, T, singles_holder);
                    rec_equivalence(node.right, T, singles_holder);
                }
                //else: returning void.
            }
        }
        //new nested_equivalence_class T1_values = new nested_equivalence_class();
        //new nested_equivalence_class T2_values = new nested_equivalence_class();
        //T1_values.rec_equivalence(T1_node, true, singles_holder); //true = T1_node.
        //T2_values.rec_equivalence(T2_node, false, singles_holder); //false = T2_node.
        return singles_holder.size() == 0;
    }

    //*/
    /*
    public void main(String[] args) {
        //for exercise 4:
        //-----
        ExpressionTree et1 = new ExpressionTree();
        String postfix1 = "4 3 7 + + 5 3 4 + + + 6 +";
        Node root1 = et1.createExprTreePostfix(postfix1);
        ExpressionTree et2 = new ExpressionTree();
        String postfix2 = "4 4 7 + + 3 6 5 + + + 3 +";
        Node root2 = et2.createExprTreePostfix(postfix2);
        System.out.print(areExpressionsEquivalent(root1, root2));
        //-----
    }
    */
}