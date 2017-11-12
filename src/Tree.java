import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.ArrayList;

public class Tree {
    private static class Node {
        int key;
        int level;
        Node left;
        Node right;
        Node p;
        private Node(int key) {
            this.key = key;
        }
        private Node(int key, Node p, int l) {
            this.key = key;
            this.p = p;
            level=l;
        }
    }

    private Node root;

    private void insert(int x) {
        root = doInsert(root, null, x, 0);
    }

    private static Node doInsert(Node node, Node p, int x, int l) {
        if (node == null) {
            return new Node(x,p,l);
        }
        if (x < node.key) {
            node.left = doInsert(node.left, node, x, l+1);
        } else if (x > node.key) {
            node.right = doInsert(node.right, node, x, l+1);
        }
        return node;
    }
    private void replace(Node a, Node b) {
        if (a.p == null)
            root = b;
        else if (a == a.p.left)
            a.p.left = b;
        else
            a.p.right = b;
        if (b != null)
            b.p = a.p;
    }
    private void change(int key, int nkey){
        remove(key);
        insert(nkey);
    }
    private void remove(Node t, int key) {
        if (t == null)
            return;
        if (key < t.key)
            remove(t.left, key);
        else if (key > t.key)
            remove(t.right, key);
        else if (t.left != null && t.right != null) {
            Node m = t.right;
            while (m.left != null)
                m = m.left;
            t.key = m.key;
            replace(m, m.right);
        } else if (t.left != null) {
            replace(t, t.left);
        } else if (t.right != null) {
            replace(t, t.right);
        } else {
            replace(t, null);
        }
    }
    private void remove(int key) {
        remove(root, key);
    }


    private void print(Node t) {
        if (t != null) {
            print(t.left);
            System.out.print(t.key+ " ");
            System.out.print(((t.left != null) ? t.left.key : 0) + " ");
            System.out.println((t.right != null) ? t.right.key : 0);
            print(t.right);
        }
    }
    public void print() {
        print(root);
        System.out.println();
    }

    private void fileRead(Tree tree) throws IOException {
        Path path = Paths.get("C:\\studyjava\\BT.txt");
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextInt()) {
            tree.insert(scanner.nextInt());
        }
        scanner.close();
    }
    private void traversal(){
        Queue<Node> q = new LinkedList<Node>();
        ArrayList<Node> ar = new ArrayList<Node>();
        Node node;
        Node node1=root;
        q.add(root);
        while (!q.isEmpty() ){
            node = q.peek();
            ar.add(node);
            if (node.left!= null)
                q.add(node.left);
            if (node.right!= null)
                q.add(node.right);
            if ( node1.level != node.level) {
                System.out.println("");
            }

            System.out.print(" "+node.key);
            System.out.print((node.p != null) ? ("(" + node.p.key + ")" ): ("(" + 0 + ")" ));

            if (!q.isEmpty()) node1=q.poll();

        }
        System.out.println("");
    }



    public static void main(String[] args) throws Exception {
        Tree tree = new Tree();

        Scanner in = new Scanner(System.in);
        System.out.println("Считать значания из файла для построения бинарного дерева поиска?");
        System.out.println("да(1)/нет(0)");
        int ansver = Integer.parseInt(in.nextLine());
        if (ansver == 1) {
            System.out.println("Бинарное дерево поиска");
            tree.fileRead(tree);
            tree.traversal();
        }
        ansver=0;
        while (ansver!=3) {
            System.out.println("Выберите действие: добавить(0)/изменить(1)/удалить(2)/выход(3):");
            ansver = Integer.parseInt(in.nextLine());
            switch (ansver) {
                case 0:
                    System.out.println("Вы выбрали добавление элемента введите его значение:");
                    break;
                case 1:
                    System.out.println("Вы выбрали изменение элемента введите: значение элемента, который нужно изменить и значение элемента, на которое нужно изменить, через пробел.");
                    break;
                case 2:
                    System.out.println("Вы выбрали удаление элемента введите его значение:");
                    break;
                case 3:
                    break;
            }
            int value;
            int value1;
            if (ansver == 0) {
                value = Integer.parseInt(in.nextLine());
                tree.insert(value);
                tree.traversal();
            }
            if (ansver == 1) {
                value = Integer.parseInt(in.next());
                value1 = Integer.parseInt(in.nextLine());
                tree.change(value, value1);
                tree.traversal();
            }
            if (ansver == 2) {
                value = Integer.parseInt(in.nextLine());
                tree.remove(value);
                tree.traversal();
            }
        }

    }


}



