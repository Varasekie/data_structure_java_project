package Matrix;


public class SinglyList<T> extends Object {
        public Node<T> head;
        //（1）构造方法
        public SinglyList()
        {
            this.head=new Node<T>();
        }
        public SinglyList(T[] values)
        {
            this();
            Node<T> rear=this.head;
            for(int i=0;i<values.length;i++)
            {
                rear.next=new Node<T>(values[i],null);
                rear=rear.next;
            }
        }
        public SinglyList(SinglyList<T> list)
        {
            this.head=list.head;
        }
        public boolean isEmpty()
        {
            return this.head.next==null;
        }
        //(2)存取
        public T get(int i)
        {
            Node<T> p=this.head.next;
            for(int j=0;p!=null&&j<i;j++)
                p=p.next;
            return(i>=0&&p!=null)?p.data:null;
        }
        /*public void set(int i,T x)
        {}*/
        public int size()
        {
            int x=0;
            Node<T> rear=this.head;
            for(int i=0;;i++)
            {
                if(rear.next!=null)
                {
                    x++;
                    rear = rear.next;
                }
                else break;

            }
            return x;
        }
        public String toString()
        {
            String str=this.getClass().getName()+"(";
            for(Node<T> p = this.head.next; p!=null; p=p.next)
            {
                str+=p.data.toString();
                if(p.next!=null)
                    str+=",";
            }
            return str+")";
        }
        //(3)插入
        public Node<T> insert(int i, T x)
        {
            if(x==null)
                throw new NullPointerException("x==null");
            Node<T> front=this.head;
            for(int j=0;front.next!=null&&j<i;j++)
                front=front.next;
            front.next=new Node<T>(x,front.next);
            return front.next;
        }
        public Node<T> insert(T x)
        {
            return insert(Integer.MAX_VALUE,x);
        }
        //(4)删除
        public T remove(int i)
        {
            Node<T> front=this.head;
            for(int j=0;front.next!=null&&j<i;j++)
                front=front.next;
            if(i>0&&front.next!=null)
            {
                T old=front.next.data;
                front.next=front.next.next;
                return old;

            }
            return null;
        }
        public void clear()
        {
            this.head.next=null;
        }
        //（5）顺序查找和基于查找算法的操作
    /*public zalei.Node<T>search(T key)
    {}
    public boolean contains(T key)
    {}
    public zalei.Node<T>inserDifferent(T x)
    {}
    public T remove(T key)
    {}
    public boolean equals(Object obj)
    {}*/
        //实验1：2-1 比较相等不能比较长度
        public boolean equals(Object obj)
        {
            if(this==obj)
                return true;
            if(obj instanceof SinglyList<?>)
            {
                SinglyList<T> compare=(SinglyList<T>)obj;
                Node<T> rear1,rear2;

                for(rear1=this.head.next,rear2=compare.head.next;rear1!=null;rear1=rear1.next,rear2=rear2.next)
                {
                    if(!(rear1.data.equals(rear2.data)))
                        return false;

                }
                if (rear2!=null)
                    return false;
            }
            return true;

        }
       //实验2：2-2判断是否子集，this是否包含list所有元素(算法较low)
        public boolean containsAll(SinglyList<T> list)
        {

            Node<T> rear1;
            Node<T> rear2;
            for(rear2=list.head.next;rear2.next!=null;rear2=rear2.next)
            {
                int m=0;
                for(rear1=this.head.next;rear1.next!=null;rear1=rear1.next)
                {
                    if (rear2.data.equals(rear1.data))
                        m+=1;
                }
                if(m!=1)
                    return false;
            }
            return true;

        }
        //实验3：2-5 返回从第i个结点开始、长度为n的子表
        public SinglyList<T> sublist1(int i, int n)
        {
            SinglyList<T> reco=new SinglyList<T>();
            int m;
            Node<T> rear1=this.head;
            for(int j=0;j<i;j++)
            {
                rear1=rear1.next;
            }
            Node<T> rear2=reco.head;
            for(;rear1!=null&&n!=0;n--)
            {
                rear2.next=new Node<T>(rear1.data,null);
                rear2=rear2.next;
                rear1=rear1.next;
            }

            return reco;

        }
        //实验3（未修改）返回从第i个结点到第n个结点的子表
        public SinglyList<T> sublist2(int i, int n)
        {
            int m=0;
            Object[] record=new Object[n-i+1];
            for(int j=0;j<=n-i;j++)
            {
                record[j]=(T)this.get(i+j);
                m=j;
            }
            SinglyList reco=new SinglyList();
            Node<T> rear=reco.head;
            for(int c=0;c<=m;c++)
            {
                rear.next=new Node<T>((T)record[c],null);
                rear=rear.next;
            }
            return reco;

        }
        //实验4：（2-11）查找首个与pattern匹配的子表（一星）  (该方法为检查实验用，rear5纯属方便检查)
        public Node<T> search(SinglyList<T> pattern)
        {
            Node<T> rear1,rear2;
            Node<T> rear4=null;
            Node<T> rear5=(Node<T>)new Node<String>("没有与pattern相匹配的子表",null);
            Node<T>[] rear3=new Node[100];
            int i=0,m=0;
            for(rear1=this.head.next,rear2=pattern.head.next;rear1!=null;rear1=rear1.next)
            {
                if(rear2.data.equals(rear1.data))
                {
                    rear3[i]=rear1;
                    i+=1;
                }
            }
            for(int j=0;j<i;j++)
            {
                for(rear1=rear3[j],rear2=pattern.head.next;rear2!=null;)
                {
                    if((rear1==null&&rear2!=null)||!(rear1.data.equals(rear2.data)))
                    {
                        m=-1;
                        break;
                    }
                    rear1=rear1.next;
                    rear2=rear2.next;
                    m=0;

                }
                if(m==0)
                {
                    rear4 = rear3[j];
                    break;
                }
                if(m==-1)
                    continue;
            }
            if(rear4!=null)
                return rear4;
            else
                return rear5;
        }
        //实验4（改）为实验5做准备，该方法以数组形式返回所有匹配子表的开始结点
        public Node<T>[] searchAll(SinglyList<T> pattern)
        {
            Node<T> rear1,rear2;
            Node<T> rear4[]=new Node[this.size()];
            Node<T> rear5[]=null;
            //rear5[0]=(Node<T>) new Node<String>("无匹配子表",null);（测试用）
            Node<T>[] rear3=new Node[100];
            int i=0,m=0,num=0;
            for(rear1=this.head.next,rear2=pattern.head.next;rear1!=null;rear1=rear1.next)
            {
                if(rear2.data.equals(rear1.data))
                {
                    rear3[i]=rear1;
                    i+=1;
                }
            }
            for(int j=0;j<i;j++)
            {
                for(rear1=rear3[j],rear2=pattern.head.next;rear2!=null;)
                {
                    if((rear1==null&&rear2!=null)||!(rear1.data.equals(rear2.data)))
                    {
                        m=-1;
                        break;
                    }
                    rear1=rear1.next;
                    rear2=rear2.next;
                    m=0;

                }
                if(m==0)
                {
                    rear4[num] = rear3[j];
                    num+=1;
                    continue;
                }
                if(m==-1)
                    continue;
            }
            if(num!=0)
               return rear4 ;
            else
                return rear5 ;
        }
        //实验4（改）为实验5做准备，该方法以数组形式返回所有匹配子表的结束的后一位节点结点
        public Node<T>[] searchEnd(SinglyList<T> pattern)
        {
            Node<T>[] rear1=this.searchAll(pattern);
            if(rear1==null)
                throw new NullPointerException("无匹配项");
            for(int i=0;rear1[i]!=null;i++)
            {
                for (Node<T> rear2 = pattern.head.next; rear2 != null;)
                {
                    rear2 = rear2.next;
                    rear1[i] = rear1[i].next;
                }
            }
            return rear1;
        }
        //实验4(改)为实验5，6做准备，该方法以数组形式返回所有匹配子表的开始前一位结点
        public Node<T>[] SearchAll(SinglyList<T> pattern)
        {
            Node<T>[] rear1=this.searchAll(pattern);
            if(rear1==null)
                throw new NullPointerException("无匹配项");
            Node<T>[] rear2=new Node[rear1.length];
            Node<T> rear3;
            for(int j=0;rear1[j]!=null;j++)
            {
                for (rear3=this.head; rear3.next != rear1[j]; )
                {
                    rear3 = rear3.next;
                }
                rear2[j]=rear3;
            }
                return rear2;

        }
        //深拷贝，返回新的相同链表
        public SinglyList<T> DeepCopy()
        {
            SinglyList<T> copy=new SinglyList<T>();
            Node<T> rear1=copy.head;
            Node<T> rear2;
            for(rear2=this.head.next;rear2!=null;)
            {
                rear1.next=new Node<T>(rear2.data,null);
                rear1=rear1.next;
                rear2=rear2.next;
            }
            return copy;

        }
        //实验5 替换所有与pattern匹配子表为list（三星）
         void replaceAll(SinglyList<T> pattern, SinglyList<T> list)
        {
            Node<T>[] rear1=this.SearchAll(pattern);
            Node<T>[] rear2=this.searchEnd(pattern);
            Node<T> rear4;
            for(int i=0;rear1[i]!=null;i++)
            {
                SinglyList<T> copy=list.DeepCopy();
                Node<T> rear3=copy.head;
                rear1[i].next= rear3.next;
                for(rear4=copy.head;rear4.next!=null;)
                {
                    rear4=rear4.next;
                }
                rear4.next=rear2[i];
            }

        }



        public static void main(String[] args) {
            String[] values1={"A","B","C","D","a","b","c","d"};
            String[] values2={"A","C","D","a","c","m"};
            String[] values3={"A","B","C","D","a","b","c","d"};
            String[] values4={"C","D","b"};
            String[] values5={"A","B","C","D"};
            String[] values6={"A","B","C","D","A","B","C","D"};
            SinglyList<String> list1=new SinglyList(values1);
            SinglyList<String> list2=new SinglyList(values2);
            SinglyList<String> list3=new SinglyList(values3);
            SinglyList<String> list4=new SinglyList(values4);
            SinglyList<String> list5=new SinglyList(values5);
            SinglyList<String> list6=new SinglyList(values6);
            System.out.println("2-1 equals方法（错误） ："+list1.equals(list2));
            System.out.println("2-1 equals方法（正确） ："+list1.equals(list3));
            System.out.println("2-2 containsAll方法（错误） ："+list1.containsAll(list2));
            System.out.println("2-2 containsAll方法（正确） ："+list1.containsAll(list3));
            System.out.println("2-5 sublist方法 ："+list1.sublist1(3,4).toString());
            System.out.println("2-5 sublist方法(容错) ："+list1.sublist1(3,7).toString());
            System.out.println("2-11 search方法(无匹配) ："+list1.search(list4).toString());
            System.out.println("2-11 search方法(有匹配) ："+list1.search(list5).toString());
            list1.replaceAll(list5,list6);
            System.out.println("2-13 repalceAll方法 ："+list1.toString());
        }


}
