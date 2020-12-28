package SecondChar;

//《数据结构与算法（Java版）（第5版）》，作者：叶核亚，2019年7月23日
//§2.4   排序线性表的存储和实现
//§2.4.1  排序顺序表
//第5版教材没写，【例8.1】用升序
//【例8.1】Java关键字集合的存储与查找，字典集合的稀疏索引技术。
//§9.5.1   顺序表的排序算法

//排序顺序表（升序）类，继承顺序表类；
//T或T的某个祖先类?实现Comparable<?>接口，提供compareTo()方法比较对象大小和相等
public class SortedSeqList<T extends Comparable<? super T>> extends SeqList<T>
{
    //2.  类的继承原则
    //（2） 子类不能继承父类的构造方法
    //构造方法
    public SortedSeqList()                       //构造空排序顺序表
    {
        super();                                 //默认调用父类构造方法SeqList()
    }
    public SortedSeqList(int length)             //构造空排序顺序表，容量为length
    {
        super(length);                           //调用父类构造方法SeqList(length)。若省略，默认调用super()
    }

    public SortedSeqList(T[] values)             //构造排序顺序表，由values数组提供元素，O(n*n)
    {
        super(values.length);                    //调用SeqList(length)。也可调用this(values.length)
//        this(values.length);
        for(int i=0; i<values.length; i++)       //直接插入排序，每趟插入1个元素
            this.insert(values[i]);              //调用子类覆盖的insert(T)方法，按值插入元素，O(n)
    }//§9.5.1   顺序表的排序算法

    //调用toPreviousString()方法得到降序的排序顺序表

    //4.  排序顺序表类覆盖父类成员方法
    //（1） 不需要从父类继承来的方法，覆盖并抛出异常
    //不支持父类的以下两个方法，将其覆盖并抛出异常
    public void set(int i, T x)                  //排序顺序表的数据元素具有只读特性，不支持
    {
        throw new java.lang.UnsupportedOperationException("set(int i, T x)");
    }
    public int insert(int i, T x)                //不支持在指定位置插入
    {
        throw new java.lang.UnsupportedOperationException("insert(int i, T x)");
    }

    //（2） 排序顺序表的插入操作
    //插入x，x!=null，根据x对象大小顺序查找确定插入位置（升序），插入在等值元素之后，返回x序号。
    //调用T的compareTo()方法比较对象大小。覆盖父类insert(x)，参数列表和返回值相同。O(n)。
    ////插入在等值结点之后。优先队列用
    //9.5.1 直接插入排序的一趟
    public int insert(T x)
    {
        int i=0;
        if(this.isEmpty() || x.compareTo(this.get(this.n-1))>0)//compareTo(T)比较大小
            i=this.n;                            //最大值尾插入，O(1)
        else
            //以下循环寻找插入位置（升序），插入在等值结点之后
            while(i<this.n && x.compareTo(this.get(i))>=0)
                i++;
        super.insert(i, x);                      //调用父类被覆盖的insert(i,x)方法，插入x作为第i个元素
        return i;
    }

    //（3） 排序顺序表的查找操作
    //顺序查找首个与key相等元素，由key的compareTo()方法确定元素的大小和相等；
    //返回元素序号i（0≤i<n），若查找不成功返回-1，O(n)。覆盖
    public int search(T key)
    {
        System.out.print(this.getClass().getName()+".search("+key+")，");
        for(int i=0;  i<this.n && key.compareTo(this.get(i))>=0;  i++)//（升序）
        {
            if(key.compareTo(this.get(i))==0)    //对象相等，运行时多态
                return i;
        }
        return -1;                               //空表或未找到时
    }

    //（4） 排序顺序表的删除操作
    //继承，不需要覆盖。运行时多态。    ////算法利用查找结果确定操作位置
//    public T remove(T key) //查找并删除首个与key相等元素，返回被删除元素；若查找不成功，返回null
//    {
//        //先查找，再调用remove(i)。若查找不成功，返回-1，不删除。其中this.search(key)执行子类的查找方法
//        return this.remove(this.search(key));
//    }
    //2015年1月30日测试，校清样，正确，不需要覆盖。

    //5.  类型的多态，子类对象即是父类对象
    //（2） 排序顺序表重载拷贝构造方法
    public SortedSeqList(SortedSeqList<? extends T> slist)//拷贝构造方法，深拷贝，O(n)。<? extends T>表示T及子类
    {
        super(slist);                  //调用SeqList(SeqList<T> list)，参数list=slist赋值相容，list引用子类实例
    }

    //由顺序表list构造排序顺序表this，深拷贝，O(n*n)；list可引用子类实例。
    //参数类型SeqList<T>中的T，是SortedSeqList<T>类声明的T，可比较大小
    public SortedSeqList(SeqList<? extends T> list)
    {
        super(list.element.length);              //调用SeqList(length)，创建空顺序表
        for(int i=0;  i<list.n;  i++)            //直接插入排序算法，每趟插入1个元素，O(n*n)
            this.insert(list.get(i));            //调用子类覆盖的insert(T)方法，按值插入，O(n)

        ////§9.5.1   顺序表的排序算法
//        super(list);                             //顺序表深拷贝，未复制元素对象，O(n)
        //采用一种排序算法对顺序表的this.element数组元素进行排序，算法省略，
        //需要访问SeqList<T>的成员变量element和n，因此两者的权限应设置为protected
//        Array9.insertSort(list.element);  //升序
        //不行，list.element类型是Object[]，不是T[]
    }
    //不行           this(list.element);          //调用SortedSeqList(T[] values)


    ////以下【研究】，第5版没写
//    public void addAll(SeqList<? extends T> list)      //继承

    //    public SeqList<T> union(SeqList<? extends T> list)   //继承，不行
    //覆盖，返回值类型不同但赋值相容，包含参数SortedSeqList<? extends T>
//    public SortedSeqList<T> union(SeqList<? extends T> list)
//    {
//        SortedSeqList<T> result = new SortedSeqList<T>(this);   //创建子类实例，深拷贝父类实例。只此一句不同
//        result.addAll(list);                     //排序顺序表合并，按值插入
//        return result;                           //返回SortedSeqList<T>对象
//    }



    //【例8.1】用升序
    //8.2   基于排序顺序表的二分法查找     第4版教材没有写，写数组的二分法查找了
    //二分法查找关键字为key元素，若查找成功返回下标，否则返回-1
    public int binarySearch(T key)
    {
        return binarySearch(key, 0, this.n-1);
//    	return SortedArray.binarySearch((T)this.element, key);  //语法错误，不能将Object[]转换成T[]
    }
    //在begin～end范围内，二分法查找关键字为key元素，若查找成功返回下标，否则返回-1。
    //若key==null，Java将抛出空对象异常；若begin、end越界，返回-1
    public int binarySearch(T key, int begin, int end)
    {
        while(begin<=end)                        //边界有效
        {
            int mid = (begin+end)/2;             //取中间位置，当前比较元素位置
            System.out.print(this.get(mid)+"? ");//显示比较中间结果，可省略
            if(key.compareTo(this.get(mid))==0)  //两对象相等
                return mid;                      //查找成功
            if(key.compareTo(this.get(mid))<0)   //key对象较小
                end = mid-1;                     //查找范围缩小到前半段
            else
                begin = mid+1;                  //查找范围缩小到后半段
        }
        return -1;                               //查找不成功
    }
    //不能调用以下方法，编译错，因为不能将Object[]转换成T[]
    //SortedArray.binarySearch((T)this.element, begin, end, key);
    //带比较器参数？？


    //第9章
    //9.5.1   顺序表的排序算法
/*    //注意，以下3方法，程序好。深拷贝仅复制数组，没有复制对象，操作后存在共用对象问题
    public SortedSeqList(T[] values)             //构造排序顺序表，由values数组提供元素
    {
        super(values);                           //构造顺序表，由values数组提供元素
        //采用一种排序算法对顺序表的this.element数组元素进行排序，算法省略

//        Array9.insertSort(this.element);         //调用排序算法，对顺序表的数组元素进行排序
//                                                 //编译错，因为不能将Object[]转换成T[]
    }

    //由顺序表list构造排序顺序表。
//    public SortedSeqList(SeqList<? extends T> list)    //SeqList<T>中的T，是SortedSeqList<T>类声明的T，可比较大小
    public SortedSeqList(SeqList<T> list)        //SeqList<T>中的T，是SortedSeqList<T>类声明的T，可比较大小
    {
        super(list);                             //顺序表深拷贝，复制顺序表，未复制元素对象。
        //以下采用一种排序算法对顺序表的this.element数组元素进行排序，算法省略，需要访问SeqList<T>的成员变量element和n，因此两者的权限应设置为protected
//      Array9.insertSort((T)this.element);      //语法错误，不能把Object[]转换成T[]
        for(int i=1; i<this.n; i++)              //直接插入排序（升序）
        {
            T temp=(T)this.element[i];           //警告：强制类型转换Object到T，不安全
            int j;
            for(j=i-1;  j>=0 && temp.compareTo((T)this.element[j])<0;  j--)  //(T)this.element[j]，T可比较大小
                this.element[j+1] = this.element[j];
            this.element[j+1] = temp;
        }
    }
*/

    //9.5.1   顺序表的排序算法
    //以下归并this和list排序顺序表（升序），this+=list，结果在this，不改变list。一次归并算法
    //第5版教材方法体省略
//    public void merge(SortedSeqList<? extends T> list)
    public void merge(SortedSeqList<T> list)
    {
        Object[] temp = this.element;
        this.element = new Object[(this.n+list.n)*2];  //扩充当前顺序表容量
        int i=0, j=0, k=0;
        while(i<this.n && j<list.n)
            if(((T)temp[i]).compareTo((T)list.element[j])<=0)
                this.element[k++]=temp[i++];
            else
                this.element[k++]=list.element[j++];

        while(i<this.n)
            this.element[k++]=temp[i++];
        while(j<list.n)
            this.element[k++]=list.element[j++];
        this.n+=list.n;
    }
    //由于Java的对象引用模型，自动析构，不会导致类似C++重复释放存储单元空间问题，但没有复制元素
    //若list==null，抛出空对象异常

    //以下返回this和list归并后的排序顺序表（升序），this+list功能，不改变this和list。一次归并算法
//    public SortedSeqList<T> mergeWith(SortedSeqList<? extends T> list)
    public SortedSeqList<T> mergeWith(SortedSeqList<T> list)
    {
        SortedSeqList<T> templist = new SortedSeqList<T>((this.n+list.n)*2);
        int i=0, j=0, k=0;
        while(i<this.n && j<list.n)
            if(((T)this.element[i]).compareTo((T)list.element[j])<=0)
                templist.element[k++]=this.element[i++];
            else
                templist.element[k++]=list.element[j++];

        while(i<this.n)
            templist.element[k++]=this.element[i++];
        while(j<list.n)
            templist.element[k++]=list.element[j++];
        templist.n=this.n+list.n;
        return templist;
    }
}
//@author：Yeheya。2014年8月19日，2019年8月27日