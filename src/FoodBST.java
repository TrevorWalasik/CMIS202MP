import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class FoodBST {
    private Node root;

    class Node {
        private Food food;
        private Node left, right;

        public Node(Food food) {
            this.food = food;
        }
    }

    public void addFood(Food food) {
        root = addFood(root, food);
    }

    private Node addFood(Node node, Food food) {
        if (node == null) {
            return new Node(food);
        }

        if (food.getCalories() < node.food.getCalories()) {
            node.left = addFood(node.left, food);
        } else {
            node.right = addFood(node.right, food);
        }

        return node;
    }

    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.println(node.food.getFoodName() + "   " + node.food.getCalories());
            printInOrder(node.right);
        }
    }

    public Hashtable<String, Integer> getFoodTable() {
        Hashtable<String, Integer> foodTable = new Hashtable<>();
        populateFoodTable(root, foodTable);
        return foodTable;
    }

    private void populateFoodTable(Node node, Hashtable<String, Integer> foodTable) {
        if (node != null) {
            populateFoodTable(node.left, foodTable);
            foodTable.put(node.food.getFoodName(), node.food.getCalories());
            populateFoodTable(node.right, foodTable);
        }
    }

    public Queue<Node> inOrderTraversal() {
        Queue<Node> queue = new LinkedList<>();
        inOrderTraversal(root, queue);
        return queue;
    }

    private void inOrderTraversal(Node node, Queue<Node> queue) {
        if (node != null) {
            inOrderTraversal(node.left, queue);
            queue.add(node);
            inOrderTraversal(node.right, queue);
        }
    }
}