package SecondChar;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SinglyList<T> {
    public Node<T> head;

    public SinglyList() {
        this.head = new Node<>();
    }

    //提供 深拷贝方法
    public SinglyList(SinglyList list) {
        this();
        Node<T> rear = this.head;
        for (Node p = list.head.next; p != null; p = p.next) {
            rear.next = new Node<T>((T) p.data, null);
            rear = rear.next;
        }
    }

    public SinglyList(T[] values) {
        this();
        Node<T> rear = this.head;

        //顺序存取
        for (T value : values) {
            if (value != null) {
                rear.next = new Node<>(value, null);
                rear = rear.next;
            }
        }
    }

    //判断是否有空串，存取元素，求长度，返回描述字符串
    public boolean isEmpty() {
        return this.head.next == null;
    }

    public T get(int i) {

        //从1开始，一直到i-1的next即是i
        Node<T> p = this.head.next;
        for (int j = 0; j < i && p != null; j++) {
            p = p.next;
        }

        //为了防止是
        return (i > 0 && p != null) ? p.data : null;
    }


    //设置值
    public void set(int i, T x) {

        if (x == null) {
            throw new NullPointerException("x == null");
        }
        //访问第i个元素
        Node<T> p = this.head.next;
        if (i >= 0) {
            for (int j = 0; j < i && p != null; j++) {
                p = p.next;
            }
            //书上是if p！= null，就=x
            p.data = x;
        } else throw new NullPointerException("无法插入");


    }


    public String toString() {
        String str = this.getClass().getName() + "(";
//        System.out.println(str);
        for (Node p = head.next; p != null; p = p.next) {
            str += p.data.toString() + (p.next == null ? "," : "");
        }
        return str + ")";
    }

    public Node<T> insert(int i, T x) {
        if (x == null) {
            throw new NullPointerException("x == null");
        }
        Node<T> front = this.head;
        for (int j = 0; j < i && front.next != null; j++) {
            front = front.next;
        }

        front.next = new Node<>(x, front.next);
        return front.next;
    }


    public Node<T> insert(T x) {
        return insert(Integer.MAX_VALUE, x);
    }

    public T remove(int i) {
        if (this.head.next == null) {
            return null;
        }
        Node<T> front = this.head;
        for (int j = 0; front.next != null && j < i; j++) {
            front = front.next;
        }
        if (i > 0 && front.next != null) {
            T x = front.next.data;
            front.next = front.next.next;
            return x;
        }
        return null;
    }

    public void clear() {
        this.head.next = null;
    }


    //非官方
    public Node<T> search(T key) {
        if (this.head.next == null) {
            return null;
        }

        for (Node<T> node = this.head.next; node != null; node = node.next) {
            if (key.equals(node.data)) {
                return node;
            }
        }
        return null;
    }

    //非官方
    public T remove(T key) {
        for (Node<T> node = this.head; node.next != null; node = node.next) {
            if (key.equals(node.next.data)) {
                //找得到就remove
                node.next = node.next.next;
                return key;
            }
        }
        return null;
    }


    //以下开始是实验
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SinglyList) {
            SinglyList<T> slist = (SinglyList<T>) obj;
            //两个链表同时循环
            //前面都和本链表相同
            Node<T> node = this.head.next, n = slist.head.next;
            while (node != null && n != null && node.data.equals(n.data)) {
                node = node.next;
                n = n.next;
            }
            return node == null && n == null;
        }
        return false;
    }

    //是否包含所有元素
    boolean containsAll(SinglyList<T> list) {
        Node<T> node = this.head.next, n = list.head.next;
        while (n != null) {
            if (!node.data.equals(n.data)) {
                return false;
            }
            n = n.next;
            node = node.next;
        }
        return true;
    }

    //深拷贝一份
    public void addAll(int i, SinglyList list) {
        int num = 1;
        SinglyList<T> list1 = new SinglyList<T>(list);
        Node<T> node = this.head.next;
        while (node != null && num < i) {
            num++;
            node = node.next;
        }
        //直接重连
        node.next = list1.head.next;
    }

    //返回并集，list和返回值深拷贝
    public SinglyList<T> union(int i, SinglyList list) {
        SinglyList<T> list_self = new SinglyList<T>(this);
        list_self.addAll(i, list);
        return list_self;
    }


    //子串的函数
    public SinglyList<T> subList(int i, int n) {
        int num = 1;
        //如果构造空串就没有head.next，因此用一个有长度的来代替
        SinglyList list_return = new SinglyList(this);
        //链接头
        SinglyList list1 = new SinglyList(this);
//        System.out.println(list1.head.next == null);
        //要深拷贝
        Node node = list1.head.next;
        for (; node != null && num <= n && num >= i; node = node.next, num++) {
            if (num == i) {
                list_return.head.next = node;
            }

            if (num == n) {
                node.next = null;
            }
        }
        //把最后的节点变成null就行了
        return list_return;
    }

    //返回删除的子表，改变list
    //就直接改前后就行了
    public SinglyList<T> remove(int i, int n) {
        int num = 1;
        for (Node node = this.head.next; node != null && num <= n && num >= i; node = node.next, i++) {
            if (num == i) {
                this.head.next = node;
            }
            if (num == n) {
                node.next = null;
            }
        }
        return this;
    }

    //	//集合差，this-=list
    public SinglyList<T> removeAll(SinglyList<T> list) {
        for (Node<T> node = this.head.next,rear = this.head; node != null&& rear !=null; ) {
            for (Node<T> node1 = list.head.next; node1 != null; node1 = node1.next) {
                if (node.data.equals(node1.data)) {
//                    this.remove(node.data);
                    //尾节点
                    if (node.next == null){
                        rear.next = null;
                        return this;
                    }
                    node = node.next;
                    rear.next = node.next.next;
                    continue;
                }
            }
            rear = rear.next;
        }
        return this;
    }

    //集合的差
    public SinglyList difference(SinglyList list) {
        //不是两个指针往后，是n平方的遍历
        for (Node<T> node = this.head.next, front = this.head; node != null; node = node.next) {
            //增加一个控制的变量，
            int find = 0;
            for (Node<T> node1 = list.head.next; node1 != null; node1 = node1.next) {
                if (node.data.equals(node1.data)) {
                    front.next = node.next;
                    find = 1;
                    break;
                }
            }
            //如果没有找到，就是删除了，front就变，如果为1，就不变
            if (find == 0) {
                front = front.next;
            }
        }
        return this;
    }

    //集合差，删除不属于list的元素
    //两个顺序不一定是一样的
    public void retainAll(SinglyList<T> list) {
        //不是两个指针往后，是n平方的遍历
        for (Node<T> node = this.head.next, front = this.head; node != null; node = node.next) {
            //增加一个控制的变量，
            int find = 0;
            for (Node<T> node1 = list.head.next; node1 != null; node1 = node1.next) {
                if (node.data.equals(node1.data)) {
                    front.next = node.next;
                    find = 1;
                    break;
                }
            }
            //如果没有找到，就是删除了，front就变，如果为1，就不变
            if (find == 0) {
                front = front.next;
            }
        }
    }


    //交集，就是只保留一样的。
    public SinglyList<T> intersection(SinglyList<T> list) {
        SinglyList<T> returnList = new SinglyList<>(this);
        for (Node<T> node = returnList.head.next, front = returnList.head; node != null; node = node.next) {
            //增加一个控制的变量，
            int find = 0;
            //找了一圈没变化，find仍然是0
            for (Node<T> node1 = list.head.next; node1 != null; node1 = node1.next) {
                //当找到一个一样的，不做任何操作
                if (node.data.equals(node1.data)) {
                    find = 1;
                    break;
                }
            }
            //没找到，把这个删了
            if (find == 0) {
                front.next = node.next;
            } else front = front.next;

            //找到了就直接front也往后。没找到front就不变
        }
        return returnList;
    }

    //查找和pattern匹配的子表
    //不一定准
    public Node<T> search(SinglyList<T> pattern) {
        //先用速度最低的，逐个匹配
        //用一个node来储存整个表都匹配的时候
        for (Node<T> node = this.head.next; node != null; node = node.next) {
            //一个标记，看是否匹配
            Node<T> pattern_node = pattern.head.next, node1 = node;
            while (pattern_node != null && node1 != null) {
                if (!pattern_node.data.equals(node1.data)) {
                    break;
                }
                pattern_node = pattern_node.next;
                node1 = node1.next;
            }
        }

        return null;
    }

    public void reverse() {

    }

    public static void main(String[] args) {
        Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Integer[] integers2 = {1, 2, 3, 4, 5, 6, 7, 8};
        SinglyList list = new SinglyList(integers);
        SinglyList list1 = new SinglyList(integers2);
        SinglyList list2 = new SinglyList(integers);


        System.out.println("removeAll" + list.removeAll(list1));

////        equals
//        System.out.println("equals" + list.equals(list1));
//
////        containsAll
//        System.out.println("containsAll" + list.containsAll(list2));
//
//        System.out.println(list.containsAll(list));
//        Integer[] integers_copy = {8, 7, 6, 5, 4, 3, 2, 1};
//
//        SinglyList copylist = new SinglyList(integers_copy);
//        System.out.println(copylist.toString());
//
//        list = new SinglyList(integers);
//        list.addAll(3, copylist);
//        System.out.println("list AddAll" + list.toString());
//
////        子串，substring方法
//        System.out.println("substring" + list.subList(1, 3));
//
////        差集的两个方法,前一个
//        list.difference(list1);
//        System.out.println("Afterdifference+ " + list);
//        list.retainAll(copylist);
//        System.out.println("retainAll" + list);
//
////        //交集
//        list = new SinglyList(integers);
//        copylist = new SinglyList(integers_copy);
//        System.out.println("交集" + list.intersection(copylist));

    }
}
