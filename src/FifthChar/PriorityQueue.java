package FifthChar;

import Last.Queue;

public class PriorityQueue<T extends Comparable<? super T>>  implements Queue<T> {
    private SortedSinglyList<T> list;            //排序单链表，存储队列元素，按升/降序排序

    public PriorityQueue(boolean asc)            //构造空队列，asc指定升序（true）或降序（false）
    {
        this.list = new SortedSinglyList<T>(asc);
    }
    public PriorityQueue()                       //构造空队列，默认升序
    {
        this(true);
    }
    public PriorityQueue(T[] objs, boolean asc)  //构造优先队列，asc指定升/降序
    {
        this(asc);
//        System.out.print("入队元素：");
        for(int i=0;  i<objs.length;  i++)
        {
//            System.out.print(objs[i]+" ");
            this.add(objs[i]);                   //元素入队
        }
//        System.out.println("\n"+this.toString());
    }

    public boolean isEmpty()                     //判断队列是否空，若为空，则返回true
    {
        return this.list.isEmpty();
    }

    public boolean add(T x)                      //元素x入队，空对象不能入队
    {
        return this.list.insert(x)!=null;        //排序单链表按值插入，比较元素大小，O(n)
    }

    public T peek()                              //返回队头元素，没有删除。若队列空，则返回null
    {
        return this.list.get(0);
    }

    public T poll()                              //出队，返回队头元素，若队列空，则返回null
    {
        return this.list.remove(0);              //返回队头元素，删除队头结点
    }

    public String toString()                     //返回队列所有元素的描述字符串
    {
        return "优先队列："+this.getClass().getName()+" "+this.list.toString();
    }
}
