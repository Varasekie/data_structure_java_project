package Last;

import SixChar.BinaryNode;
import SixChar.BinaryTree;

public class Trees<T> {
    public DoubleNode<T> root;

    public Trees() {
        this.root = null;
    }

    //用标明空子树的先根构造二叉树
//    public Trees(T[] prelist) {
//        this.root = create(prelist);
//    }

    //深拷贝
    public Trees(Trees<T> binaryTree) {
        copy(this.root);
    }

    public DoubleNode<T> copy(DoubleNode<T> node) {
        DoubleNode<T> tBinaryNode = null;
        if (node == null) {
            return tBinaryNode;
        }
        tBinaryNode = new DoubleNode<>(node.data);
        tBinaryNode.down = copy(node.down);
        tBinaryNode.next = copy(node.next);

        return tBinaryNode;
    }


    @Override
    public String toString() {
        return toString(this.root, "");
    }

    //递归描述字符串
    private String toString(DoubleNode<T> node, String tab) {
        if (node == null) {
            return "";
        }
        return tab + node.data.toString() + "\n" + toString(node.next, tab + "\t") + toString(node.down, tab + "\t");
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    //二叉树插入节点,插入根节点，插入值x作为孩子节点
    public void insert(T x) {
        if (x != null) {
            this.root = new DoubleNode<>(x, this.root, null);
        }
    }

    //新插入的作为p的左节点/右节点
    public BinaryNode<T> insert(BinaryNode<T> p, boolean left, T x) {
        if (x == null || p == null) {
            return null;
        }
        if (left) {
            return p.left = new BinaryNode<>(x, p.left, null);
        }
        return p.right = new BinaryNode<>(x, null, p.right);
    }

    //布尔值决定左子树和右子树
    public void remove(BinaryNode<T> p, boolean left) {
        if (p != null) {
            if (left) {
                p.left = null;
            } else p.right = null;
        }
    }

    public void clear() {
        this.root = null;
    }

}
