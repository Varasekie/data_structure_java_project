package NinthChar;

public class Array9 {

    //§9.1.1 直接插入排序
    public static void insertSort(int[] keys, boolean asc) //直接插入排序，asc取值true（升序）、false（降序）
    {
        System.out.println("直接插入排序（"+(asc?"升":"降")+"序）");
        for(int i=1; i<keys.length; i++)         //n-1趟，依次向前插入n-1个数
        {
            int x = keys[i],  j;                 //每趟将keys[i]插入到前面排序子序列中
            for(j=i-1; j>=0 && (asc ? x<keys[j] : x>keys[j]); j--)
                keys[j+1] = keys[j];             //将前面较大/小元素向后移动
            keys[j+1] = x;                       //x值到达插入位置
            System.out.print("第"+i+"趟，x="+x+"，");
            Array1.print(keys,0,i);              //输出已排序子序列{,}，可省略，见例1.4
            Array1.print(keys,i+1);              //输出待排序子序列
        }
    }

    //对象数组的直接插入排序，算法同上
    public static <T extends Comparable<? super T>> void insertSort(T[] value)
    {
        System.out.println("直接插入排序（升序）");
        for(int i=1; i<value.length; i++)       //n-1趟
        {
            T x = value[i];                      //每趟将value[i]插入到前面排序子序列中
            int j;
            for(j=i-1; j>=0 && x.compareTo(value[j])<0; j--)  //将前面较大元素向后移动
                value[j+1] = value[j];
            value[j+1] = x;                      //x值到达插入位置
            System.out.print("第"+i+"趟: ");
            Array1.printBracket(value);          //见例1.4，调用print(Object)输出排序中间结果，可省略
        }
    }

    //§9.1.2   希尔排序
    public static void shellSort(int[] keys)     //希尔排序（升序）
    {
        System.out.println("希尔排序（升序）");
        for(int delta=keys.length/2;  delta>0;  delta/=2)  //若干趟，控制增量每趟减半
        {
            for(int i=delta; i<keys.length; i++) //一趟分若干组，每组直接插入排序
            {
                int x=keys[i], j;                //keys[i]是当前待插入元素
                for(j=i-delta; j>=0 && x<keys[j]; j-=delta)//循环组内直接插入排序，寻找插入位置
                    keys[j+delta] = keys[j];     //每组元素相距delta远
                keys[j+delta] = x;               //插入元素
            }
            System.out.print("delta="+delta+"  ");
            Array1.print(keys);
        }
    }

    //§9.2.1   冒泡排序
    private static void swap(int[] keys, int i, int j)     //交换keys[i]与keys[j]元素，i、j范围由调用者控制
    {
        int temp = keys[j];
        keys[j] = keys[i];
        keys[i] = temp;
    }

    public static void bubbleSort(int[] keys)    //冒泡排序（升序）
    {
        System.out.println("冒泡排序（升序）");
        boolean exchange=true;                   //是否有交换的信号量
        for(int i=1; i<keys.length && exchange; i++)     //有交换时再进行下一趟，最多n-1趟
        {
            exchange=false;                      //假定元素未交换
            for(int j=0; j<keys.length-i; j++)   //一趟比较、交换
            {
                if(keys[j]>keys[j+1])            //相邻元素比较，若反序，则交换
                {
                    swap(keys, j, j+1);
                    exchange=true;               //有交换
                }
            }
            System.out.print("第"+i+"趟，下标0～"+(keys.length-i)+"，");
            Array1.print(keys);
        }
    }
    //以下第5版教材没写
/*    public static void bubbleSort(int[] keys)              //冒泡排序（升序）
    {
        bubbleSort(keys, true);
    }
    public static void bubbleSort(int[] keys, boolean asc) //冒泡排序，asc取值true（升序）、false（降序）
    {
        System.out.println("冒泡排序（"+(asc?"升":"降")+"序）");
        boolean exchange=true;                             //是否交换的标记
        for(int i=1;  i<keys.length && exchange;  i++)     //有交换时再进行下一趟，最多n-1趟
        {
            exchange=false;                                //假定元素未交换
            for(int j=0;  j<keys.length-i;  j++)           //一趟比较、交换
                if(asc ? keys[j]>keys[j+1] : keys[j]<keys[j+1])//相邻元素比较，若反序，则交换
                {
                    swap(keys, j, j+1);
                    exchange=true;                         //有交换
                }
            System.out.print("第"+i+"趟，下标0～"+(keys.length-i)+"，");
            Array1.print(keys,0,i);              //输出待排序子序列
            Array1.print(keys,i,keys.length-i);              //输出已排序子序列{,}，可省略，见例1.4
            System.out.println();
        }
    }*/


    //§9.2.2   快速排序
    public static void quickSort(int[] keys)     //快速排序（升序）
    {
        System.out.print("快速排序（升序）");
        quickSort(keys, 0, keys.length-1);
    }
    //对存于keys数组begin～end之间的子序列进行一趟快速排序（升序），递归算法
    private static void quickSort(int[] keys, int begin, int end)
    {
        if(begin>=0 && begin<end && end<keys.length)//序列有效
        {
            int i=begin, j=end;                  //i、j下标分别从子序列的前后两端开始
            int x = keys[i];                     //选取子序列第一个值作为基准值x
            while(i!=j)
            {
                while(i<j && keys[j]>=x)         //从后向前寻找较小值移动，不移动与基准值相等元素
                    j--;
                if(i<j)
                    keys[i++]=keys[j];           //子序列后端较小元素向前移动

                while(i<j && keys[i]<=x)         //从前向后寻找较大值移动，不移动与基准值相等元素
                    i++;
                if(i<j)
                    keys[j--]=keys[i];           //子序列前端较大元素向后移动
            }
            keys[i] = x;                         //基准值x到达最终位置
            System.out.print("\n下标"+begin+"～"+end+"， x="+x+"，  ");
            Array1.print(keys);
            quickSort(keys, begin, i-1);         //前端子序列再排序，递归调用
            quickSort(keys, i+1, end);           //后端子序列再排序，递归调用
        }
    }

    //§9.3.1   直接选择排序
    public static void selectSort(int[] keys)    //直接选择排序（升序）
    {
        System.out.println("直接选择排序（升序）");
        for(int i=0; i<keys.length-1; i++)       //n-1趟排序
        {
            int min=i;
            for(int j=i+1; j<keys.length; j++)   //每趟在从keys[i]开始的子序列中寻找最小元素
                if(keys[j]<keys[min])
                    min = j;                    //min记住本趟最小元素下标
            System.out.print("第"+(i+1)+"趟，下标"+i+"～"+(keys.length-1)+"，min="+min+"，");
            if(min!=i)                           //将本趟最小元素交换到前边
                swap(keys, i, min);              //交换keys[i]与keys[min]，方法见9.2.1节
            Array1.print(keys);
        }
    }

    //§9.3.2   堆排序
    public static void heapSort(int[] keys)      //堆排序（降序），最小堆
    {
        for(int i=keys.length/2-1;  i>=0;  i--)  //创建最小堆，根结点值最小
            sift(keys, i, keys.length-1);
        System.out.println("最小堆？ "+isHeap(keys,true));
//        System.out.println("最小堆？ "+isMinHeap(keys));
//        System.out.println("最大堆？ "+isHeap(keys,false));
        System.out.println("堆排序（降序）：");
        for(int i=keys.length-1; i>0; i--)       //堆排序，每趟将最小值交换到后面，再调整成最小堆
        {
            swap(keys, 0, i);                    //交换keys[0]与keys[i]，方法见9.2.1节
            sift(keys, 0, i-1);
        }
    }
    //将keys数组中以parent为根的子树调整成最小堆，子序列范围为parent～end
    private static void sift(int[] keys, int parent, int end)
    {
        System.out.print("sift("+parent+"～"+end+")，");
        int child = 2*parent+1;                  //child是parent的左孩子
        int x = keys[parent];                    //当前子树的原根值
        while(child<=end)                        //沿较小值孩子结点向下筛选
        {
            if(child<end  &&  keys[child+1]<keys[child])//最小堆，若右孩子值更小
//            if(child<end  &&  keys[child+1]>keys[child])//最大堆，若右孩子值更大
                child++;                         //child记住孩子值较小者
            if(x>keys[child])                    //若父母结点值较大，最小堆
//            if(x<keys[child])                  //若父母结点值较小，最大堆
            {
                keys[parent] = keys[child];      //则将较小孩子结点值上移
                parent = child;                  //parent、child两者都向下一层
                child = 2*parent+1;
            }
            else
                break;
        }
        keys[parent] = x;                        //x调整后的位置
        Array1.print(keys);
    }

    //【实验题9-1，习题解答】循环
    //判断keys关键字序列是否是最小/大堆，若minheap取值为true，则最小堆；否则最大堆。
    //堆序列可以是空序列。算法使用循环检查每棵子树
    public static boolean isHeap(int[] keys, boolean minheap)
    {
        for(int i=keys.length/2-1; i>=0; i--)  //i从最深一棵子树的根结点开始
//        for(int i=0; i<keys.length/2; i++)     //i从根结点开始，到最深一棵子树的根结点
        {
            int left=2*i+1;                      //left是i的左孩子，肯定存在
            if(minheap ? (keys[i]>keys[left] || left+1<keys.length && keys[i]>keys[left+1])
                    : (keys[i]<keys[left] || left+1<keys.length && keys[i]<keys[left+1]))
                return false;                    //若父母结点值较大/小，则不是最小/大堆
        }
        return true;
    }

    //【实验题9-1，单元测验】递归算法
    public static boolean isMinHeap(int[] keys)  //判断keys关键字序列是否是最小堆
    {
        return isMinHeap(keys, 0);
    }
    //判断以i为根的子树是否为最小堆，先根遍历递归算法
    private static boolean isMinHeap(int[] keys, int i)
    {
        boolean yes = true;                      //必须是true
        if(i<keys.length/2)                      //若有左孩子，递归调用，判断两棵子树
        {
            int left=2*i+1;                      //left是i的左孩子，肯定存在
            System.out.println("i="+i+"，left="+left+(left+1<keys.length?("，left+1="+(left+1)):""));
            if(keys[i]>keys[left] || left+1<keys.length && keys[i]>keys[left+1])
                return false;                    //不是最小堆

            //以下先判断左子树，若是堆且有右子树，再判断右子树，yes变量记得左/右子树结果；
            ////同查找算法
            yes = isMinHeap(keys,left);          //递归调用左子树
            if(yes && left+1<keys.length)
                yes = isMinHeap(keys,left+1);    //递归调用右子树
            //若右子树返回false，返回给其父母结点

            ////上述必须3句，不能合并如下。
            //① 下句若左子树返回false，没有递归调用右子树；但是，yes仍然是true，错误。
//            if(isMinHeap(keys,left) && left+1<keys.length)
//                yes = isMinHeap(keys,left+1);
            //② 不行。因为当没有右子树时，left+1<keys.length返回false，错误
//            return isMinHeap(keys,left) && (left+1<keys.length && isMinHeap(keys,left+1));
        }
        return yes;
    }


    //§9.4   归并排序
    //一次归并
    //将X中分别以begin1、begin2开始的两个相邻子序列归并（升序）到Y中，子序列长度为n
    private static void merge(int[] X, int[] Y, int begin1, int begin2, int n)
    {
        int i=begin1, j=begin2, k=begin1;
        while(i<begin1+n && j<begin2+n && j<X.length) //将X中两个相邻子序列归并到Y中
        {
            if(X[i]<=X[j])                       //将较小值复制到Y中
                Y[k++]=X[i++];
            else
                Y[k++]=X[j++];
        }

        while(i<begin1+n && i<X.length)          //将前一个子序列剩余元素复制到Y中，子序列长度可能不足n
            Y[k++]=X[i++];
        while(j<begin2+n && j<X.length)          //将后一个子序列剩余元素复制到Y中
            Y[k++]=X[j++];
    }

    //一趟归并，将X中若干相邻子序列两两归并（升序）到Y中，子序列长度为n
    private static void mergepass(int[] X, int[] Y, int n)
    {
        System.out.print("子序列长度n="+n+"  ");
        for(int i=0; i<X.length; i+=2*n)         //将X中若干相邻子序列归并到Y中
            merge(X, Y, i, i+n, n);              //一次归并
        Array1.print(Y);
    }

    public static void mergeSort(int[] X)        //归并排序（升序）
    {
        System.out.println("归并排序（升序）：");
        int[] Y = new int[X.length];             //Y数组长度同X数组
        int n=1;                                 //排序子序列长度，初值为1
        while(n<X.length)
        {
            mergepass(X, Y, n);                  //一趟归并，将X中若干相邻子序列归并到Y
            n*=2;                                //子序列长度加倍
            if(n<X.length)
            {
                mergepass(Y, X, n);              //一趟归并，将Y中若干相邻子序列再归并到X
                n*=2;
            }
        }
    }
}
