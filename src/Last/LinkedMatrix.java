package Last;

public class LinkedMatrix {
    //private
    int rows, columns;                           //矩阵行数、列数
    SeqList<SortedSinglyList<Triple>> rowlist;   //行指针顺序表，元素是排序单链表，默认权限
//    SeqList<PolySinglyList<Triple>> rowlist;     //行指针顺序表，元素是多项式排序单链表，默认权限
//  SeqList<SortedSeqList<Triple>> rowlist;      //行指针顺序表，元素是排序顺序表
    ////说明：rows, columns教材写private；此时不写，因为实验题要用
    ////说明：list不能private，因为7.2.2节 图的邻接表 要遍历边单链表。

    //构造m×n矩阵，元素为0。若m<0或n<0，参数错，抛出无效参数异常
    public LinkedMatrix(int m, int n)
    {
        if(m>=0 && n>=0)
        {
            this.rows = m;
            this.columns = n;
            this.rowlist = new SeqList<SortedSinglyList<Triple>>(m); //构造顺序表，元素初值为null
//            this.rowlist = new SeqList<PolySinglyList<Triple>>(m);
            ////说明：m指定顺序表容量，是否m<min，由顺序表判断和更改。所以，本类不要声明min。
            for(int i=0; i<m; i++)                         //顺序表增加m个空单链表
                this.rowlist.insert(new SortedSinglyList<Triple>()); //顺序表尾插入，自动扩容；排序单链表默认升序
//                this.rowlist.insert(new PolySinglyList<Triple>());
            ////说明：m=0，n=0，空图，不能抛出异常，因为空图也要创建空间。
        }
        else
            throw new IllegalArgumentException("矩阵行列数不能<0，m="+m+"，n="+n);
    }

    public LinkedMatrix(int n)                   //构造n×n矩阵，元素为0
    {
        this(n, n);
    }
    public LinkedMatrix()                        //构造0×0矩阵；存储容量为最小值。//说明：空图用
    {
        this(0, 0);
    }

    public LinkedMatrix(int m, int n, Triple[] tris)//构造m×n矩阵，由tris三元组数组提供元素值
    {
        this(m, n);
        for(int i=0; i<tris.length; i++)
            this.set(tris[i]);                   //按行主序插入一个元素的三元组
    }

    //构造m×n矩阵，tris字符串指定三元组形式的元素序列，“,”分隔，没有空格
    public LinkedMatrix(int m, int n, String tris)
    {
        this(m, n);
        int start=0, end=0;
        while(start<tris.length() && (end=tris.indexOf(')',start))!=-1)
        {
            this.set(new Triple(tris.substring(start,end+1)));  //start～end子串是一个三元组
            start=end+2;
        }
    }
    //何时用到？？

    public int getRows()                         //返回矩阵行数。方法体省略
    {
        return this.rows;
    }
    public int getColumns()                      //返回矩阵列数。方法体省略
    {
        return this.columns;
    }

    //（2） 返回矩阵元素
    //返回矩阵第i行第j列元素。若i、j序号越界，则抛出序号越界异常。查找算法比较三元组大小
    public int get(int i, int j)
    {
        if(i>=0 && i<this.rows && j>=0 && j<this.columns)
        {
            //在第i行排序单链表中顺序查找三元组(i,j,0)，按位置比较三元组大小
            DoubleNode<Triple> find = this.rowlist.get(i).search(new Triple(i,j,0));
            return (find!=null) ? find.data.value : 0;     //若查找成功，返回元素值，否则返回0
        }
//        else////可以没有
        throw new IndexOutOfBoundsException("i="+i+"，j="+j);
    }

    //（3） 设置矩阵元素
    //设置矩阵第i行第j列元素为x。若i、j序号越界，则抛出序号越界异常。
    //查找、插入、删除算法均比较三元组大小
    public void set(int i, int j, int x)
    {
        if(i>=0 && i<this.rows && j>=0 && j<this.columns)
        {
            SortedSinglyList<Triple> link = this.rowlist.get(i);//获得第i行排序单链表
            if(x==0)
                link.remove(new Triple(i,j,0));  //若查找成功，删除(i,j,?)结点
            else
            {   //以下查找再插入或替换元素操作，遍历link排序单链表二次
                Triple tri = new Triple(i,j,x);
                DoubleNode<Triple> find = link.search(tri);  //顺序查找tri，若有元素>tri，则查找不成功
                if(find!=null)
                    find.data.value = x;         //查找成功，修改修改find引用对象的成员变量值
                else
                    link.insert(tri);           //查找不成功，排序单链表link按(i,j)次序插入三元组tri
            }
        }
        else
            throw new IndexOutOfBoundsException("i="+i+"，j="+j);//抛出序号越界异常
    }

    public void set(Triple tri)                  //以三元组tri设置矩阵元素
    {
        this.set(tri.row, tri.column, tri.value);
    }

    //（4） 输出矩阵
    public String toString()                     //返回稀疏矩阵三元组行的单链表描述字符串
    {
        String str="";
        for (int i=0; i<this.rowlist.size(); i++)//循环次数为行指针顺序表长度
            str += i+" -> "+this.rowlist.get(i).toString()+"\n";//获得第i行排序单链表的描述字符串
        return str;
    }

    public void printMatrix()                    //输出矩阵
    {
        System.out.println("矩阵"+this.getClass().getName()+"（"+rows+"×"+columns+"）：");
//        for (int i=0; i<this.rowlist.size(); i++)
        for(int i=0; i<this.rows; i++)
        {
            DoubleNode<Triple> p = this.rowlist.get(i).head.next;//遍历第i行排序单链表
            for(int j=0; j<this.columns; j++)
            {
                if(p!=null && j==p.data.column)   //有i==p.data.row
                {
                    System.out.print(String.format("%4d", p.data.value));
                    p = p.next;
                }
                else
                    System.out.print(String.format("%4d", 0));
            }
            System.out.println();
        }
    }

    //【思考题5-1】
    //求矩阵非0元素最小值的三元组，使用TripleComparator比较器比较Triple对象大小
    public Triple minValue()
    {
        java.util.Comparator<Triple> comp = new TripleComparator();  //比较器
        Triple min=null;
        for(int i=0; i<this.rows; i++)
        {
            //遍历第i行排序单链表，求矩阵元素最小值，使用比较器comp比较Triple对象大小
            for(DoubleNode<Triple> p=this.rowlist.get(i).head.next;  p!=null;  p=p.next)
                if(min==null || comp.compare(p.data, min)<0)
                    min = p.data;
        }
        return min;
    }


    ////说明：可行，效率低，用于测试get(i,j)方法是否正确。教材不写
    public void printMatrix2()                   //输出矩阵
    {
        System.out.println("矩阵"+this.getClass().getName()+"（"+rows+"×"+columns+"）：");
        for(int i=0; i<this.rows; i++)
        {
            for(int j=0; j<this.columns; j++)
                System.out.print(String.format("%4d", this.get(i,j)));
            System.out.println();
        }
    }

    //（5） 比较矩阵相等
    public boolean equals(Object obj)            //比较this与obj矩阵是否相等
    {
        if(this==obj)
            return true;
        if(obj instanceof LinkedMatrix)
        {
            LinkedMatrix mat=(LinkedMatrix)obj;
            return this.rows==mat.rows && this.columns==mat.columns && this.rowlist.equals(mat.rowlist);
        }
        return false;
    }

    //（6） 设置矩阵行列数
    //设置矩阵为m行n列。若m指定行数较大，则将行指针顺序表扩容，使用原各行单链表。
    //用于7.2.2节图的邻接表存储结构。
    public void setRowsColumns(int m, int n)
    {
        if(m>=0 && n>=0)
        {
            if(m>this.rows)                                //若m参数指定行数较大
                for(int i=this.rows; i<m; i++)
//                    this.rowlist.insert(new SortedSinglyList<Triple>()); //顺序表尾插入，自动扩容
                    this.rowlist.insert(new PolySinglyList<Triple>());
            ////说明：以下两句不能写在if中，图删除顶点后，设置行列数小。
            ////说明：而且写在最后，因为要用原值，此算法与构造方法不同，所以，不与构造方法合并。
            ////说明：m=0，n=0，设置为空图，不抛出异常。
            this.rows = m;
            this.columns = n;
        }
        else
            throw new IllegalArgumentException("矩阵行列数不能<0，m="+m+"，n="+n);
    }

    //（7） 矩阵相加
    //矩阵相加，this+=mat，合并两行排序单链表的算法同多项式相加
    public void addAll(LinkedMatrix mat)
    {
        if(this.rows==mat.rows && this.columns==mat.columns)
            for(int i=0; i<this.rows; i++)
                this.rowlist.get(i).addAll(mat.rowlist.get(i));  //调用多项式排序单链表相加算法
        else
            throw new IllegalArgumentException("两个矩阵阶数不同，不能相加");
    }
}
//以上教材5.2.2节，【实验题5-3】见ExerciseLinkedMatrix类。
//@author：Yeheya。2016-2-12，2019年9月8日

//第10章，10.2 实现迭代器
//10.2.1   提供迭代器的类
//【例10.2】  使用迭代器遍历顺序表和单链表。
/*    public void printMatrix()                    //输出矩阵。使用顺序表和单链表的迭代器遍历
    {
        System.out.println("矩阵"+this.getClass().getName()+"（"+rows+"×"+columns+"）：");
//        java.util.Iterator<PolySinglyList<Triple>> seqit = this.rowlist.iterator();//获得顺序表迭代器
//        while (seqit.hasNext())                           //遍历顺序表
        for (PolySinglyList<Triple> link : this.rowlist)  //逐元循环，link获得rowlist集合中的每个元素
        {
//            java.util.Iterator<Triple> it = seqit.next().iterator();//获得当前行单链表的迭代器
            java.util.Iterator<Triple> it = link.iterator();//获得单链表的迭代器
            Triple triple= it.hasNext() ? it.next() : null;//获得迭代器的第1个元素
            for (int j=0; j<this.columns; j++)             //遍历第i行排序单链表
                if (triple!=null && triple.column==j)      //有三元组，非0元素
                {
                    System.out.print(String.format("%4d", triple.value));
                    triple= it.hasNext() ? it.next() : null;//获得迭代器的下一个元素
                }
                else System.out.print(String.format("%4d", 0));
            System.out.println();
        }
    }
}

    //使用单链表迭代器遍历，可用删除。但替换未成功。因为，查找到的元素要替换，不仅是包含。
/*    public void set(Triple tri)        //以三元组tri设置矩阵元素。若tri的行/列序号越界，抛出序号越界异常
    {
        int i=tri.row, j=tri.column;
        if (i>=0 && i<this.rows && j>=0 && j<this.columns)
        {
//            SortedSinglyList<Triple> link = this.rowlist.get(i);//获得第i行排序单链表
            java.util.Iterator<Triple> it = this.rowlist.get(i).iterator();//获得第i行排序单链表的迭代器对象
            while (it.hasNext())                           	//若有后继元素，使用迭代器遍历一个集合
//          System.out.print(it.next().toString()+" ");       	//返回后继元素
            if (tri.value==0)
                it.remove();                          //删除(i,j,?)结点（顺序查找，如果有）
//            link.remove(tri);                          //删除(i,j,?)结点（顺序查找，如果有）
            else
            {
                Node<Triple> find=link.search(tri);        //顺序查找首次出现元素
                if (find!=null)
                    find.data.value = tri.value;           //查找成功，修改矩阵元素值
                else link.insert(tri);                     //查找不成功，按(i,j)次序插入tri。// 二次遍历

//也可，一次遍历
//                Node<Triple> find = link.insertUnrepeatable(tri);    //插入不重复元素x，返回x结点
//                find.data.value = tri.value;      //修改该结点元素
            }
        }
        else throw new IndexOutOfBoundsException("i="+i+"，j="+j);//抛出序号越界异常
    }*/

