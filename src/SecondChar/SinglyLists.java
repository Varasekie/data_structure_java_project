package SecondChar;

//单链表操作类
public class SinglyLists {
    //元素不重复
    public static <T> boolean isDifferent(SinglyList<T> list) {
        //是n平方的遍历
        //每一个都和之后的比较，降低时间复杂度
        for (Node<T> node = list.head.next; node != null; node = node.next) {
            Node<T> node_after = node.next;
            //设置一个变量
            boolean find = true;

            for (; node_after != null; node_after = node_after.next) {
                //有一个相等就可以直接跳出循环，直接执行外侧的
                //如果没有一个相等，说明这个元素互异
                if (node.data.equals(node_after.data)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static <T extends Comparable<? super T>> boolean isSorted(SinglyList<T> list, boolean asc) {
        //判断是否排序
        for (Node<T> node = list.head.next; node.next != null; node = node.next) {
            if (!asc == (node.data.compareTo(node.next.data) <= 0)) {
                //如果是不符合这个排序的
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Integer[] integers = {1, 1, 3, 4, 4, 6, 7, 8};
        SinglyList<Integer> list = new SinglyList<Integer>(integers);
        System.out.println(SinglyLists.isDifferent(list));


        System.out.println(SinglyLists.isSorted(list,true));
    }
}
