package Last;

public class MatrixGraph<T> extends AbstractGraph<T>{
    protected Matrix matrix;                     //矩阵对象，存储图的邻接矩阵

    public MatrixGraph()                         //构造空图，顶点数为0，边数为0
    {
        super();                                 //构造空顶点顺序表，默认容量
        this.matrix = new Matrix(0,0);           //构造0×0矩阵，默认容量
    }
    public MatrixGraph(T[] vertexes)             //以vertexes顶点集合构造图，边集合为空
    {
        this();
        for(int i=0; i<vertexes.length; i++)
            this.insert(vertexes[i]);            //插入顶点，稍后说明算法
    }
    public MatrixGraph(T[] vertexes, Triple[] edges)  //以vertexes顶点集合和edges边集合构造图
    {
        this(vertexes);                          //以vertexes顶点集合构造图，没有边
        for(int j=0; j<edges.length; j++)
            this.insert(edges[j]);               //插入边，稍后说明算法
    }

    public String toString()                     //返回图的顶点集合和邻接矩阵描述字符串
    {
        String str = super.toString()+"邻接矩阵:  \n";
//        str+=this.matrix.toString();
        int n = this.vertexCount();              //顶点数
        for(int i=0; i<n; i++)
        {
            for(int j=0; j<n; j++)
            {
                if(this.matrix.get(i,j)==MAX_WEIGHT)
                    str += "     ∞";
                else
                    str += String.format("%6d", this.matrix.get(i,j));
            }
            str+="\n";
        }
        return str;
//        return super.toString()+"邻接矩阵:  \n"+AbstractGraph.toString(this.matrix.element);
    }
    //【例7.1】  构造带权图。

    //（2）插入顶点
    public int insert(T x)                       //插入元素为x的顶点，返回x顶点序号
    {
        this.vertexlist.insert(x);               //顶点顺序表尾插入x，自动扩容
        int i = this.vertexlist.size()-1;        //获得插入顶点x的序号
        if(i >= this.matrix.getRows())           //若邻接矩阵容量不够
            this.matrix.setRowsColumns(i+1, i+1); //则矩阵扩容。保持邻接矩阵行列数同图的顶点数
        for(int j=0; j<i; j++)                   //初始化第i行、列元素值为∞。i==j值已为0
        {
            this.matrix.set(i, j, MAX_WEIGHT);
            this.matrix.set(j, i, MAX_WEIGHT);
        }
        return i;                                //返回插入顶点序号
    }

    //（3）插入边
    public void insert(int i, int j, int w)      //插入边〈vi,vj〉，权值为w
    {
        if(i!=j)                                 //不能表示自身环
        {
            if(w<=0 || w>MAX_WEIGHT)             //边的权值容错，视为无边，取值∞
                w=MAX_WEIGHT;
            this.matrix.set(i,j,w);              //设置矩阵元素[i,j]值为w。若i、j越界，抛出序号越界异常
        }
        else
            throw new IllegalArgumentException("不能插入自身环，i="+i+"，j="+j);
    }
    public void insert(Triple edge)              //插入一条边
    {
        this.insert(edge.row, edge.column, edge.value);
    }

    //（4）删除边
    public void remove(int i, int j)             //删除边〈vi,vj〉，忽略权值
    {
        if(i!=j)
            this.matrix.set(i, j, MAX_WEIGHT);   //设置边的权值为∞。若i、j越界，则抛出序号越界异常
    }
    public void remove(Triple edge)              //删除边，忽略权值
    {
        this.remove(edge.row, edge.column);
    }

    //（5）删除顶点
    public T remove(int i)   //删除顶点vi及其所有关联的边，返回顶点vi元素。用最后一个顶点替换顶点vi
    {
        //super.remove(i);       ////也可，第5版教材没写，
        int n=this.vertexCount();                //原顶点数
        if(i>=0 && i<n)
        {
            T x = this.vertexlist.get(n-1);      //最后一个顶点元素
            this.vertexlist.set(i, x);           //将第i个顶点元素替换为最后一个
            x = this.vertexlist.remove(n-1);     //删除最后一个顶点（顶点数减1）。  //顺序表删除，若i越界，返回null

            for(int j=0;  j<n;  j++)             //将第i行元素替换为第n-1行
                this.matrix.set(i, j, this.matrix.get(n-1,j));
            for(int j=0;  j<n;  j++)             //将第i列元素替换为第n-1列
                this.matrix.set(j, i, this.matrix.get(j, n-1));
            this.matrix.setRowsColumns(n-1, n-1);//邻接矩阵少一行一列
            return x;
        }
        else
            throw new IndexOutOfBoundsException("i="+i);  //抛出序号越界异常
    }

    //（5） 获得邻接顶点和边的权值属性
    public int weight(int i, int j)              //返回<vi,vj>边的权值。用于图的最小生成树、最短路径等算法
    {
        return this.matrix.get(i,j);             //返回矩阵元素[i,j]值。若i、j越界，抛出序号越界异常
    }

    //返回顶点vi在vj后的后继邻接顶点序号 ；若j=-1，返回vi的第一个邻接顶点序号；若不存在后继邻接顶点，返回-1。用于7.3节图的遍历算法
    protected int next(int i, int j)
    {
        int n=this.vertexCount();
        if(i>=0 && i<n && j>=-1 && j<n && i!=j)
            for(int k=j+1;  k<n;  k++)           //当j=-1时，k从0开始寻找后继邻接顶点
                if(this.matrix.get(i,k)>0 && this.matrix.get(i,k)<MAX_WEIGHT)//权值表示有边
                    return k;
        return -1;
    }
}
