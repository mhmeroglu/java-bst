import java.util.Scanner;

class Node {
    int key;
    Node l, r;

    public Node(int data) {
        key = data;
        l = r = null;
    }
}

public class BST {
    Node root;

    BST() {
        root = null;
    }

    void insert(int key) {
        root = insertRec(root, key);
    }

    Node insertRec(Node root, int key) {
        if (root == null) {
            root = new Node(key);
            return root;
        }

        if (key <= root.key)
            root.l = insertRec(root.l, key);
        else if (key > root.key)
            root.r = insertRec(root.r, key);

        return root;
    }

    void inorderTra(Node root) {
        if (root != null) {
            inorderTra(root.l);
            System.out.print(root.key + " ");
            inorderTra(root.r);
        }
    }
    
    int findParent(Node root, int value) {
        Node parent = null;
        Node current = root;
        while (current != null) {
            if ((current.l != null && current.l.key == value) || (current.r != null && current.r.key == value)) {
                parent = current;
                break;
            }
            if (value <= current.key) {
                current = current.l;
            } else {
                current = current.r;
            }
        }
        if (parent != null)
            return parent.key;
        else
            return value;
    }

    Node deleteNode(Node root, int key) {
        if (root == null) return root;

        if (key < root.key)
            root.l = deleteNode(root.l, key);
        else if (key > root.key)
            root.r = deleteNode(root.r, key);
        else {
            if (root.l == null)
                return root.r;
            else if (root.r == null)
                return root.l;

            root.key = minValue(root.r);

            root.r = deleteNode(root.r, root.key);
        }
        return root;
    }

    int minValue(Node root) {
        int min_v = root.key;
        while (root.l != null) {
            min_v = root.l.key;
            root = root.l;
        }
        return min_v;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        BST bSTree = new BST();

        while (scn.hasNextLine()) {
            String line = scn.nextLine();
            String[] input = line.split(" ");
            String command = input[0];

            if (line.isEmpty() || command.equalsIgnoreCase("EXIT") || command.equals(".")) {
                System.out.println("Program ended.");
                return;
            }

            switch (command) {
                case "CONSTRUCT":
                    String inputStr = input[1];
                    inputStr = inputStr.substring(1, inputStr.length() - 1);
                    String[] elements = inputStr.split(",");
                    for (String element : elements) {
                        bSTree.insert(Integer.parseInt(element));
                    }
                    break;
                case "LIST":
                    bSTree.inorderTra(bSTree.root);
                    System.out.println();
                    break;
                case "INSERT":
                    if (input.length != 2) {
                        System.out.println("Invalid input format for INSERT command. Expected: INSERT value");
                        break;
                    }
                    int insertValue;
                    try {
                        insertValue = Integer.parseInt(input[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input format for INSERT command. Expected: INSERT value");
                        break;
                    }
                    bSTree.insert(insertValue);
                    System.out.println("The parent of " + insertValue + " is " + bSTree.findParent(bSTree.root, insertValue));
                    break;
                case "PARENT":
                    if (input.length != 2) {
                        System.out.println("Invalid input format for PARENT command. Expected: PARENT value");
                        break;
                    }
                    int parentValue;
                    try {
                        parentValue = Integer.parseInt(input[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input format for PARENT command. Expected: PARENT value");
                        break;
                    }
                    int parentKey = bSTree.findParent(bSTree.root, parentValue);
                    if (parentKey == parentValue) {
                        System.out.println("It is a root node.");
                    } else {
                        System.out.println("The parent of " + parentValue + " is " + parentKey);
                    }
                    break;
                case "DELETE":
                    if (input.length != 2) {
                        System.out.println("Invalid input format for DELETE command. Expected: DELETE value");
                        break;
                    }
                    int deleteValue;
                    try {
                        deleteValue = Integer.parseInt(input[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input format for DELETE command. Expected: DELETE value");
                        break;
                    }
                    int oldRoot = bSTree.root.key;
                    bSTree.root = bSTree.deleteNode(bSTree.root, deleteValue);
                    if (bSTree.root == null) {
                        System.out.println("Tree is now empty");
                        break;
                    }
                    if (bSTree.root.key != oldRoot) {
                        System.out.println("Root changed. The new root is " + bSTree.root.key);
                    }
                    break;
                default:
                    System.out.println("Enter a valid command!");
                    break;
            }
        }
    }
}