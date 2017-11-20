package BTree;
import java.io.*;
import java.util.*;

/**
 * Класс служит для хранения узлов бинарного дерева со свойством
 * <b>root</b>.
 * @author Julia Dosaeva
 * @version 1.0
 */

public class Tree {
    /** Свойство - корень*/
    private Node root;

    /** Получает ссылку на корнь бинарного дерева.
     * @return  Ссылку на корень дерева.
     */
    Node getRoot(){
        return root;
    }

    /**
     * Вызов метода добавления узла дерева {@link Tree#doInsert(Node, Node, int, int)}
     * @param x Установливает значения ключа, добавляемого узла.
     */
    void insert(int x) {
        root = doInsert(root, null, x, 1);
    }

    /**
     * Добавляет новый узел дерева, если  дерево пустое, создает новый корень,
     * иначе добавляет к существующему, с соблюдением условия - ключ правого сына больше ключа родителя,
     * ключ левого сына меньше ключа родителя. Добавление узла с неуникальным ключом не выполняется. Задаются свойства
     * @param node - узел дерева
     * @param p - родительский узел
     * @param x - значение ключа, добляемого узла
     * @param l - уровень добавляемого узла
     * @return -  добавленный узел дерева
     */
    Node doInsert(Node node, Node p, int x, int l) {
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

    /** Замещает узел дерева
     * @param a на узел дерева
     * @param b
     */
    void replace(Node a, Node b) {
        if (a.p == null)
            root = b;
        else if (a == a.p.left)
            a.p.left = b;
        else
            a.p.right = b;
        if (b != null){
            b.p = a.p;
        }
    }

    /** Замена узла дерева, старый узел удаляется, новый добавляется.
     * @param key - задает знаяение ключа, которое необходимо изменить
     * @param nkey - задает знаяение ключа, на которое необходимо изменить
     */
    void change(int key, int nkey){
        remove(key);
        insert(nkey);
    }

    /**
     * Удаление узла дерева
     * @param t - узел дерева
     * @param key - значение ключа.
     */
    void remove(Node t, int key) {
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
        }
        else if (t.left != null)
        {
            replace(t, t.left);
        }
        else if (t.right != null)
        {
            replace(t, t.right);
        }
        else {
            replace(t, null);
        }
    }

    /**Вызов метода  {@link Tree#remove(Node, int)} для удаления узла дерева
     * @param key - ключ удаляемого узла.
     */
    void remove(int key) {
        remove(root, key);
    }

    /**Чтение ключей для создания дерева из файла формата txt
     * @param tree - ссылка на дерево
     * @throws FileNotFoundException - искомый файл не найден.
     */
    void fileRead(Tree tree) throws FileNotFoundException {
        try {
            FileReader f = new FileReader("BT.txt");
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextInt()) {
                tree.insert(scanner.nextInt());
            }
            scanner.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("Требуемый файл не найден или не существует.");
        }
    }

    /** Поиск узла дерева по ключу
     * @param t - узел дерева
     * @param key - ключ узла
     * @return - узел дерева.
     */
    Node search(Node t,int key){
        if (t == null || t.key ==key)
            return t;
        if (key < t.key)
            return search(t.left, key);
        else
            return search(t.right, key);
    }

    /** Вызов метода поиска узла дерева {@link Tree#search(Node, int)}
     * @param key - ключ узла
     * @return - узел дерева.
     */
    Node search(int key) {
        return search(root, key);
    }

    /**Вычисление высоты дерева
     * @return - количество узлов дерева.
     */
    int heightTree(){
        if (root==null) return 0;
        Queue<Node> q = new LinkedList<Node>();
        int tree_level = 1;
        Node node_current;
        Node node_previous = root;
        q.add(root);
        while (!q.isEmpty()){
            node_current = q.peek();
            if( node_current.p != null && node_current.p.level != node_current.level-1)
                node_current.level=node_current.p.level+1;
            if (node_current.p == null && node_current.level!=1 )
                node_current.level=1;
            if ( node_current.level != node_previous.level)
                tree_level++;
            if (node_current.left!= null)
                q.add(node_current.left);
            if (node_current.right!= null)
                q.add(node_current.right);
            if (!q.isEmpty()) node_previous=q.poll();
        }
        return tree_level;
    }
    /** Вывод дерева на консоль*/
    void print() {
        if (root != null) {
            Queue<Node> q = new LinkedList<Node>();
            List<String> ar = new ArrayList<String>();
            List<String> ar_p = new ArrayList<String>();
            List<String> separator = new ArrayList<String>();
            int index_p;
            int max_level = heightTree();
            Node node;
            Node node_previous = root;
            q.add(root);
            for (int j = 0; j < ((int) Math.pow(2, max_level) - 1); j++) {
                separator.add(" ");
            }
            while (!q.isEmpty()) {
                node = q.peek();
                if (node_previous.level != node.level) {
                    for (String x : separator) {
                        System.out.print(x);
                    }
                    System.out.println("");
                    for (String x : ar) {
                        System.out.print(x);
                    }
                    ar_p.clear();
                    for (int i = 0; i < ar.size(); i++) {
                        ar_p.add(ar.get(i));
                    }
                    ar.clear();
                    separator.clear();
                    for (int m = 0; m < ((int) Math.pow(2, max_level) - 1); m++) {
                        separator.add(" ");
                    }
                }
                if (node_previous.level != node.level) {
                    for (int j = 0; j < ((int) Math.pow(2, max_level) - 1); j++) {
                        ar.add(" ");
                    }
                }
                if (node.p == null) {
                    for (int j = 0; j < ((int) Math.pow(2, max_level) - 1); j++) {
                        ar.add(" ");
                    }
                    ar.add((int) (Math.pow(2, max_level) / 2) - 1, Integer.toString(node.key));
                } else {
                    index_p = ar_p.indexOf(Integer.toString(node.p.key));
                    if (node.p.left != null && node.p.left.key == node.key) {
                        ar.set(index_p - ((int) (Math.pow(2, max_level) / (int) Math.pow(2, node.level))), Integer.toString(node.key));
                        if (node.p.key > 9) separator.set((index_p - 1), " /");
                        else
                            separator.set((index_p - 1), "/");
                    }
                    if (node.p.right != null && node.p.right.key == node.key) {
                        ar.set(index_p + ((int) (Math.pow(2, max_level) / (int) Math.pow(2, node.level))), Integer.toString(node.key));
                        if (node.p.key > 9) separator.set(index_p + 1, "\\");
                        else
                            separator.set(index_p + 1, "\\");
                    }
                }
                if (node_previous.level != node.level) {

                    System.out.println("");

                }
                if (node.p == null) {
                    ar_p.clear();
                    for (int i = 0; i < ar.size(); i++) {
                        ar_p.add(ar.get(i));
                    }
                }
                if (node.left != null)
                    q.add(node.left);
                if (node.right != null)
                    q.add(node.right);
                if (!q.isEmpty()) node_previous = q.poll();
            }
            for (String x : separator) {
                System.out.print(x);
            }
            System.out.println("");
            for (String x : ar) {
                System.out.print(x);
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) throws Exception {
        Console console = new Console();
        console.readConsole();
    }
}
