package Last;

public class Matrix {
    protected int rows, columns;                 //矩阵行数、列数
    protected int[][] element;                   //二维数组，存储矩阵元素
    private static final int MIN_CAPACITY=5;     //常量，指定element数组容量的最小值

    //构造m×n矩阵，元素为0。若m<0或n<0，参数错，抛出无效参数异常
    public Matrix(int m, int n)
    {
        if(m>=0 && n>=0)
        {
            this.rows = m;
            this.columns = n;
            if(m<MIN_CAPACITY)                   //若0≤m,n<MIN_CAPACITY，element数组容量取最小值
                m=MIN_CAPACITY;
            if(n<MIN_CAPACITY)
                n=MIN_CAPACITY;
            this.element = new int[m][n];        //数组元素初值为0
        }
        else
            throw new IllegalArgumentException("矩阵行列数不能<0，m="+m+"，n="+n);
    }       ////说明：m=0，n=0，空图，不能抛出异常，因为空图也要创建空间。

    public Matrix(int n)                         //构造n×n矩阵，元素为0
    {
        this(n, n);
    }
    public Matrix()                              //构造0×0矩阵；存储容量为最小值。//说明：空图用
    {
        this(0, 0);
    }

    public Matrix(int m, int n, int[][] values)   //构造m×n矩阵，由values[][]提供元素
    {
        this(m, n);
        for(int i=0;  i<values.length && i<m;  i++)//values元素不足时补0，忽略多余元素
            for(int j=0;  j<values[i].length && j<n;  j++)
                this.element[i][j] = values[i][j];
    }

    public int getRows()                         //返回矩阵行数
    {
        return this.rows;
    }
    public int getColumns()                      //返回矩阵列数
    {
        return this.columns;
    }
    public int get(int i, int j)                 //返回第i行第j列元素。若i、j序号越界，抛出序号越界异常
    {
        if(i>=0 && i<this.rows && j>=0 && j<this.columns)
            return this.element[i][j];
        throw new IndexOutOfBoundsException("i="+i+"，j="+j);
    }
    public void set(int i, int j, int x)         //设置第i行第j列元素为x。若i、j序号越界，抛出序号越界异常
    {
        if(i>=0 && i<this.rows && j>=0 && j<this.columns)
            this.element[i][j]=x;
        else
            throw new IndexOutOfBoundsException("i="+i+"，j="+j);
    }

    public String toString()                     //返回矩阵所有元素的描述字符串，行主序遍历
    {
        String str=" 矩阵"+this.getClass().getName()+"（"+this.rows+"×"+this.columns+"）：\n";
        for(int i=0;  i<this.rows;  i++)
        {
            for(int j=0;  j<this.columns;  j++)
                str+=String.format("%6d", this.element[i][j]); //"%6d"格式表示十进制整数占6列
            str += "\n";
        }
        return str;
    }

    //设置矩阵为m行n列。若参数指定行列数较大，则将矩阵扩容，并复制原矩阵元素。
    //用于7.2.1节图的邻接矩阵存储结构
    public void setRowsColumns(int m, int n)
    {
        if(m>=0 && n>=0)
        {
            ////说明：element容量不会<MIN_CAPACITY，所以，不需要比较m,n<MIN_CAPACITY等，只需比较m、n是否更大。
            //参数指定的行数或列数较大时，扩充二维数组容量
            if(m>this.element.length || n>this.element[0].length)
            {
                int[][] source = this.element;
                this.element = new int[m*2][n*2];          //重新申请二维数组空间，元素初值为0
                for(int i=0;  i<this.rows;  i++)           //复制原二维数组元素
                    for(int j=0;  j<this.columns;  j++)
                        this.element[i][j] = source[i][j];
            }
            ////说明：以下两句不能写在if中，图删除顶点后，设置行列数小。
            ////说明：而且写在最后，因为要用原值，此算法与构造方法不同，所以，不与构造方法合并。
            ////说明：m=0，n=0，设置为空图，不抛出异常。
            this.rows = m;
            this.columns = n;
        }
        else
            throw new IllegalArgumentException("矩阵行列数不能<0，m="+m+"，n="+n);
    }
}
/*
程序设计说明如下：
（1）试图在以下构造方法中调用setRowsColumns(int m, int n)方法，未成功，算法不同。算了吧，两者重点不同。
    public Matrix(int m, int n)
*/
//【实验题5-1】见ExerciseMatrix类

