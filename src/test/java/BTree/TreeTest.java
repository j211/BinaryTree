package BTree;

import org.junit.Test;
import org.junit.Assert;

import static org.junit.Assert.*;

public class TreeTest {
    @Test
    public void testInsert() throws Exception {
        Tree tree = new Tree();
        tree.fileRead(tree);
        tree.insert(33);
        assertNotNull (tree.search(33));
    }
    @Test
    public void testRemove() throws Exception {
        Tree tree = new Tree();
        tree.fileRead(tree);
        tree.insert(33);
        tree.remove(33);
        assertNull (tree.search(33));

    }
}