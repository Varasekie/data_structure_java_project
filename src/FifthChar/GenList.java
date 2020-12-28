package FifthChar;

public class GenList<T> implements GenLList {
    public GenNode<T> head;

    public GenList() {
        this.head = new GenNode<T>();
    }

    public GenList(T data) {
        //头节点的data存储表明
        this.head = new GenNode<>(data, null, null);
    }

    public GenList(T data, T[] atoms) {
        //atmos指定原子初值
        this.head = new GenNode<T>(data, null, null);
        GenNode<T> rear = this.head;
        ;
        for (int i = 0; i < atoms.length; i++) {
            rear.next = new GenNode<T>(atoms[i], null, null);
            rear = rear.next;
        }
    }

    @Override
    public boolean isEmpty() {
        return this.head.next == null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int depth() {
        return 0;
    }

    @Override
    public GenNode get(int i) {
        if (i < 0) {
            i = 0;
        }
        if (i > this.depth()) {
            i = this.depth();
        }
        int j = 0;
        GenNode<T> node = this.head.next;
        while (j<=i && node.next !=null){
            node = node.next;
            j++;
        }
        return node;
    }

    @Override
    public GenNode insert(int i, Object x) {
        if (i < 0) {
            i = 0;
        }
        if (i > this.depth()) {
            i = this.depth();
        }
        int j = 0;
        //先让指针指到那边
        GenNode<T> node = this.head.next;
        while (j<=i && node.next !=null){
            node = node.next;
           j++;
        }
        //然后插入
        GenNode<T> newNode = new GenNode<T>((T)x,null,node.next);
        node.next = newNode;
        return newNode;
    }

    @Override
    public GenNode insert(int i, GenList genList) {
        return null;
    }

    @Override
    public GenNode remove(int i) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public GenNode search() {
        return null;
    }

    @Override
    public GenNode remove(Object key) {
        return null;
    }

    //记住
    public String toString(GenList<T> genlist) {
        String str = (this.head.data == null ? "" : genlist.head.data.toString()) + "(";
        for (GenNode node = genlist.head.next; node != null; node = node.next) {
            str += toString(node) + (node.next == null ? "" : ",");

        }
        return str + ")";
    }

    public String toString(GenNode<T> node) {
        if (node == null) {
            return null;
        }
        return node.child == null ? toString(node.child) : node.data.toString();
    }


    public static void main(String[] args) {
        //注意这里要用包装类
        Integer[] ints = new Integer[]{1,2,3,4,5};
        GenList<Integer> list = new GenList<Integer>(123,ints);
    }
}
