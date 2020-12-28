package EigthChar;

import ForthChar.Queue;
import SecondChar.Node;

public class LinkedQueue <T>  implements Queue<T> {
    private Node<T> front, rear;                 //front和rear分别指向队头和队尾结点

    public LinkedQueue()                         //构造空队列
    {
        this.front=this.rear=null;
    }
    public boolean isEmpty()                     //判断队列是否空，若空返回true
    {
        return this.front==null && this.rear==null;
    }

    @Override
    public boolean add() {
        return false;
    }

    public boolean add(T x)                      //元素x入队，x!=null，若操作成功返回true
    {
        if(x==null)
            return false;
        Node<T> q = new Node<T>(x, null);
        if(this.front==null)
            this.front=q;                        //空队插入
        else
            this.rear.next=q;                    //队列尾插入
        this.rear=q;
        return true;
    }

    public T peek()                              //返回队头元素，没有删除。若队列空，则返回null
    {
        return this.isEmpty() ? null : this.front.data;
    }

    public T poll()                              //出队，返回队头元素，若队列空，则返回null
    {
        if(isEmpty())
            return null;
        T x = this.front.data;                   //取得队头元素
        this.front = this.front.next;            //删除队头结点
        if(this.front==null)
            this.rear=null;
        return x;
    }

    public String toString()                     //返回队列所有元素的描述字符串，形式为“(,)”
    {
        StringBuffer strbuf = new StringBuffer(this.getClass().getName()+"(");
        for(Node<T> p=this.front;  p!=null;  p=p.next)
            strbuf.append(p.data.toString()+(p.next!=null?",":""));
        return new String(strbuf+")");           //由StringBuffer对象构造String对象
    }
}
