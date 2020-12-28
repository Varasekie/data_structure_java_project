package SixChar;

import ForthChar.LinkedStack;
import ForthChar.Stack;


public class BinaryTree<T> {
    public BinaryNode<T> root;

    public BinaryTree() {
        this.root = null;
    }

    //用标明空子树的先根构造二叉树
    public BinaryTree(T[] prelist) {
        this.root = create(prelist);
    }

    //深拷贝
    public BinaryTree(BinaryTree<T> binaryTree) {
        copy(this.root);
    }

    public BinaryNode<T> copy(BinaryNode<T> node) {
        BinaryNode<T> tBinaryNode = null;
        if (node == null) {
            return tBinaryNode;
        }
        tBinaryNode = new BinaryNode<T>(node.data);
        tBinaryNode.left = copy(node.left);
        tBinaryNode.right = copy(node.right);

        return tBinaryNode;
    }

    //i作为全局变量
    int i = 0;

    private BinaryNode<T> create(T[] prelist) {
        BinaryNode<T> node = null;
        if (i < prelist.length) {
            T elem = prelist[i++];
            if (elem != null) {
                node = new BinaryNode<>(elem);
                node.left = create(prelist);
                node.right = create(prelist);
            }
        }
        return node;
    }


    @Override
    public String toString() {
        return toString(this.root, "");
    }

    //递归描述字符串
    private String toString(BinaryNode<T> node, String tab) {
        if (node == null) {
            return "";
        }
        return tab + node.data.toString() + "\n" + toString(node.left, tab + "\t") + toString(node.right, tab + "\t");
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    //二叉树插入节点,插入根节点，插入值x作为孩子节点
    public void insert(T x) {
        if (x != null) {
            this.root = new BinaryNode<T>(x, this.root, null);
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

    //先根遍历二叉树
    public void preorder() {
        preorder(this.root);
        System.out.println();
    }

    public void preorder(BinaryNode<T> p) {
        if (p != null) {
            System.out.println(p.data.toString());
            preorder(p.left);
            preorder(p.right);
        }
    }

    //中根
    public void inorder() {
        inorder(this.root);
    }

    public void inorder(BinaryNode<T> p) {
        if (p != null) {
            preorder(p.left);
            System.out.println(p.data.toString());
            preorder(p.right);
        }
    }

    //后根
    public void postorder() {
        porstorder(this.root);
    }

    public void porstorder(BinaryNode<T> p) {
        if (p != null) {
            preorder(p.left);
            preorder(p.right);
            System.out.println(p.data.toString());
        }
    }

    //先根+中根构造二叉树


    public int size() {
        return size(this.root);
    }

    public int size(BinaryNode<T> p) {
        if (p == null) {
            return 0;
        }
        return 1 + size(p.left) + size(p.right);
    }

    //非递归，栈遍历
    public void preorderTraverse() {
        Stack<BinaryNode<T>> stack = new LinkedStack<BinaryNode<T>>();
        BinaryNode p = this.root;
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                System.out.println(p.data + "");
                stack.push(p);
                p = p.left;
            } else {
                System.out.println("^");
                p = stack.pop();
                p = p.right;
            }
        }
        System.out.println("^");
    }

    public void leaf() {
        AboutLeaf(this.root);
    }

    private void AboutLeaf(BinaryNode<T> node) {
        if (node.left == null && node.right == null) {
            System.out.println(node);
        }
        if (node.left != null) {
            AboutLeaf(node.left);
        }
        if (node.right != null) {
            AboutLeaf(node.right);
        }
    }


    int j = 0;

    private void AboutLeafCount(BinaryNode<T> node) {
        if (node.left == null && node.right == null) {
//            System.out.println(node);
            j++;
        }
        if (node.left != null) {
            AboutLeafCount(node.left);
        }
        if (node.right != null) {
            AboutLeafCount(node.right);
        }
    }

    //叶子节点数
    public int LeafCount() {
        AboutLeafCount(this.root);
//        System.out.println(j);
        return j;
    }

    public int height() {
        return AboutHeight(this.root);
    }

    //先根次序
    private int AboutHeight(BinaryNode<T> node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = AboutHeight(node.left);
        int rightHeight = AboutHeight(node.right);
        if (leftHeight >= rightHeight) {
            return leftHeight + 1;
        } else return rightHeight + 1;
    }

    //返回第一个相等的
    //用栈
    public BinaryNode<T> search(T key) {
        Stack<BinaryNode<T>> stack = new LinkedStack<BinaryNode<T>>();
        BinaryNode p = this.root;
        while (p != null || !stack.isEmpty()) {

            if (p != null) {
                if (key.equals(p.data)) {
                    return p;
                }
                stack.push(p);
                p = p.left;
            } else {
                p = stack.pop();
                p = p.right;
            }
        }
        return null;
    }

    //删除首个以key相等元素为根的子树
    //栈实现
    public BinaryNode<T> remove(T key) {
        Stack<BinaryNode<T>> stack = new LinkedStack<BinaryNode<T>>();
        BinaryNode p = this.root;
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                if (key.equals(p.data)) {
                    p.left = null;
                    p.right = null;
                    return p;
                }
                stack.push(p);
                p = p.left;
            } else {
                p = stack.pop();
                p = p.right;
            }
        }
        return null;
    }

    //删除所有以key相等元素为根的子树
    //递归实现
    public void removeAll(T key) {
        remove(key, this.root);
    }

    private void remove(T key, BinaryNode<T> node) {
        if (node != null) {
            remove(key, node.left);
            remove(key, node.right);
            if (key.equals(node.data)) {
                node.left = null;
                node.right = null;
            }
        }

    }

    //返回父节点
    public BinaryNode<T> parent(BinaryNode<T> node) {

        if (node == null) {
            return null;
        }

        return findParent(this.root, node);
    }

    BinaryNode<T> parent = null;
    boolean flag = false;

    private BinaryNode<T> findParent(BinaryNode<T> node, BinaryNode<T> key) {

        //如果已经找到了，就直接一路返回
        if (flag) {
            return parent;
        }
        //接下来都是还没找到的情况
        if (node == null) {
            return null;
        }

        if ((node.left != null && key.data.equals(node.left.data)) || (node.right != null && key.data.equals(node.right.data))) {
            flag = true;
            parent = node;
            return parent;
        }
        BinaryNode<T> left = findParent(node.left, key);
        BinaryNode<T> right = findParent(node.right, key);

        //左子树右子树都没有
        if (left == null && right == null) {
            return null;
        }
        if (left != null) {
            return parent = left;
        }
        //优先是left
        else {
            return parent = right;
        }

    }

    //返回key相等元素所在节点的层次
    //先根遍历
    int level = -1;

    public int level(T key) {
        return findLevel(this.root, key);

    }

    private int findLevel(BinaryNode<T> node, T key) {
        if (node == null) {
            return level;
        }
        if (key.equals(node.data)) {
            return level + 1;
        }

        //如果没找到，递归找
        int left = findLevel(node.left, key);
        int right = findLevel(node.right, key);
        //说明两个都没找到，此时层数不增加
        if (left == -1 && right == -1) {
            return level;
        }
        level = (left > right) ? (right == -1) ? left + 1 : right + 1 : left + 1;
        return level;
    }

    //先根和中根遍历
    public BinaryTree(T preList[], T inlist[]) {
        this.root = createBinary(preList, inlist, 0, preList.length-1, inlist.length);
    }

    private BinaryNode<T> createBinary(T[] preList, T[] inList, int start, int end, int length) {
        //start是先根开始
        //end是中根结束
        if (preList == null || inList == null || length <= 0) {
            return null;
        }
        BinaryNode<T> node =  new BinaryNode<>(preList[start]);

        if (length == 1){
            return node;
        }
        int i;
        for (i = 0; i < length; i++) {
            if (preList[start].equals(inList[end - i])) {
                break;
            }
//                length++;
        }

        //切割数组,仿照深拷贝
//            System.out.println(node.toString());
        node.left = createBinary(preList, inList, start + 1, end - i - 1, length - i - 1);
        node.right = createBinary(preList, inList, start + length - i, end, i);

        return node;
    }

    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj instanceof BinaryTree) {
            obj = (BinaryTree) obj;

        }

        return false;
    }

    public static void main(String[] args) {
        String[] prelist = {"A", "B", "D", null, "G", null, null, null, "C", "E", null, null, "F", "C", null, null, "B"};
        BinaryTree<String> bitree = new BinaryTree<>(prelist);
//        System.out.println(bitree.toString());

//        System.out.println("先根");
//        bitree.preorder();
//        System.out.println("中根");
//        bitree.inorder();

//        叶子节点
//        System.out.println("叶子节点");
//        bitree.leaf();

//        叶子节点数
//        System.out.println("叶子节点数");
//        System.out.println(bitree.LeafCount());
////
//        System.out.println("高度");
//        int height = bitree.height();
//        System.out.println(height);
////
//        //寻找A节点
//        System.out.println("寻找A节点");
//        System.out.println(bitree.search("A"));

//
        //删除首个以key相等元素为根的子树
        //栈实现
//        bitree.remove("B");
//        System.out.println(bitree.toString());
        //删除所有以key相等元素为根的子树
        //递归实现
//        System.out.println("删除所有以key相等元素为根的子树");
//        bitree.removeAll("B");
//        System.out.println(bitree);
//
//        //返回层数
//        System.out.println("返回层数");
////        System.out.println(bitree.level("M"));
//        System.out.println(bitree.level("B"));
////        System.out.println(bitree.level("G"));
//
////        返回父母节点
//        System.out.println();
//        System.out.println(bitree.parent(new BinaryNode<String>("B")));

        String[] prelist1 = {"A", "B", "D", "E", "G", "C", "F", "H"};
        String[] inlist = {"D", "B", "G", "E", "A", "F", "H", "C"};

        BinaryTree<String> tree = new BinaryTree<>(prelist1, inlist);
        System.out.println(tree.toString());
//        tree.preorder();


    }
}
