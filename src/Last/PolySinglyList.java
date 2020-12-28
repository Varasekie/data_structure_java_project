package Last;
import Matrix.Addible;

public class PolySinglyList <T extends Comparable<? super T> & Addible<? super T>> extends SortedSinglyList<T>{
    public PolySinglyList(boolean asc)           //构造方法，asc指定升/降序
    {
        super(asc);                              //创建空单链表
    }
    public PolySinglyList()                      //构造方法，默认升序
    {
        super();
    }

    public PolySinglyList(T[] terms, boolean asc)//构造方法，由项数组指定多项式各项值
    {
        super(terms, asc);
    }
    public PolySinglyList(PolySinglyList<T> list)//拷贝构造方法
    {
        super(list);                             //单链表深拷贝，复制所有结点，没有复制对象
    }

    //多项式相加，this+=list，不改变list。this、list的升/降序属性必须一致。重载
    public void addAll(PolySinglyList<T> list)
    {
        if(this.asc!=list.asc)
            throw new java.lang.IllegalArgumentException("this.asc!=list.asc，两条排序单链表的升/降序属性不同，不能运算");

        DoubleNode<T> front=this.head,  p=front.next;  //front是p的前驱结点
        DoubleNode<T> q=list.head.next;
        while(p!=null && q!=null)
        {
            if(p.data.compareTo(q.data)==0)      //若两项大小相同，
            {
                p.data.add(q.data);              //则两项相加，add()方法由Addible<T>接口约定
                if(p.data.removable())           //若元素满足删除条件，removable()方法由Addible<T>接口约定
                    front.next=p.next;           //相加后元素不需要存储，则删除p结点
                else
                    front = front.next;
                p = front.next;
                q = q.next;
            }
            else
            {
                if(asc ? p.data.compareTo(q.data)>0 : p.data.compareTo(q.data)<0)
                {
                    front.next = new DoubleNode<T>(q.data, p);  //复制q结点并插入到front结点之后、p结点之前
                    q = q.next;
                }
                else
                    p = p.next;
                front = front.next;
            }
        }

        while(q!=null)                          //将list单链表中剩余结点复制并插入到this链表尾
        {
            front.next = new DoubleNode<T>(q.data, null);
            front = front.next;
            q = q.next;
        }
    }

    //以下PolySinglyList<T>类研究，调试过，算法功能达不到期望，第5版教材没写
    //加法（并集），返回this+poly的多项式。
    //即返回复制this和list所有结点的多项式排序单链表，不改变this和list。
    public PolySinglyList<T> union(PolySinglyList<T> list)
    {
        if(this.asc!=list.asc)
            throw new java.lang.IllegalArgumentException("this.asc!=list.asc，两条排序单链表的升/降序属性不同，不能运算");

        DoubleNode<T> p = this.head.next;
        DoubleNode<T> q = list.head.next;
        PolySinglyList<T> poly = new PolySinglyList<T>();
        DoubleNode<T> rear = poly.head;
        while(p!=null && q!=null)
        {
            if(p.data.compareTo(q.data)==0)      //若两项大小相同
            {
                T tobj = p.data;
                tobj.add(q.data);                //两项相加，add()方法由Addible<T>接口约定
                ////算法错误，上两句是引用，没有复制对象，结果是p.data+=q.data;

                if(!tobj.removable())            //若相加结果不是需要删除的条件，则创建结点
                {
                    rear.next=new DoubleNode<T>(tobj, null);//创建结点，引用对象，没有复制对象，new TermXY()
                    rear=rear.next;
                }
                p = p.next;
                q = q.next;
            }
            else if(p.data.compareTo(q.data)<0)
            {
                rear.next = new DoubleNode<T>(p.data, null);  //复制p结点并插入到rear结点之后。没有复制对象
                rear=rear.next;
                p = p.next;
            }
            else
            {
                rear.next = new DoubleNode<T>(q.data, null);  //复制q结点并插入到rear结点之后
                rear=rear.next;
                q = q.next;
            }
        }
        if(p==null)
            p=q;
        while(p!=null)                          //将p结点及之后的剩余结点复制并插入到cpol链表尾
        {
            rear.next = new DoubleNode<T>(p.data, null);
            rear=rear.next;
            p = p.next;
        }
        return poly;
    }
    ////说明：算法思路正确。如果没有用T（TermX），直接复制int，则算法结果正确。
    //但是因为做不到复制TermX对象，导致算法结果不正确。
    //所以，java.util集合中只声明addAll()，没有声明union()。
}
