package BTree;

class Node {
    int key;
    int level;
    Node left;
    Node right;
    Node p;
    Node(int key) {
        this.key = key;
    }
    Node(int key, Node p, int l) {
        this.key = key;
        this.p = p;
        level=l;
    }
}
