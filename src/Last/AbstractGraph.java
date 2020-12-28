package Last;

public abstract class AbstractGraph<T> implements Graph<T> {

    protected static final int MAX_WEIGHT = 0x00ffffff; //最大权值（表示无穷大∞）
    ////注意：不能用Integer.MAX_VALUE，因为最短路径要加，会溢出成负数

    protected SeqList<T> vertexlist;             //顶点顺序表，存储图的顶点集合

    public AbstractGraph()                       //构造空图，顶点数为0
    {
        this.vertexlist = new SeqList<T>();      //构造空顺序表，默认容量
    }

    public int vertexCount()                     //返回图的顶点数
    {
        return this.vertexlist.size();           //返回顶点顺序表的元素个数
    }

    public String toString()                     //返回图的顶点集合描述字符串
    {
        return "顶点集合：" + this.vertexlist.toString() + "\n";
    }

    public T get(int i)                          //返回顶点vi元素；若i越界，则返回null
    {
        return this.vertexlist.get(i);
    }////遍历用

    public void set(int i, T x)                  //设置顶点vi元素为x
    {
        this.vertexlist.set(i, x);                //若i越界，则抛出异常
    }

    //以下抽象方法没有方法体，由子类提供实现
//    public abstract int insert(T x);           //尾插入元素为x的顶点，返回x顶点序号
//    public abstract T remove(int i);           //删除顶点vi及其所有关联的边
//    public abstract int weight(int i, int j);    //返回<vi,vj>边的权值

    public int search(T key)                     //查找并返回首个与key相等元素的顶点序号
    {
        return this.vertexlist.search(key);
    }

    public T remove(T key)                       //查找并删除首个与key相等元素顶点及其关联的边
    {
        return this.remove(this.search(key));    //删除顶点vi及其关联的边，抽象方法，待子类实现，37行
    }

    //返回vi在vj后的后继邻接顶点序号；若j=-1，返回vi的第0个邻接顶点序号；若不存在后继邻接顶点，返回-1
    protected abstract int next(int i, int j);

    //§7.3   图的遍历
    //§7.3.1   图的深度优先遍历
    public void DFSTraverse(int i)               //图从顶点vi出发的一次深度优先遍历，包含非连通图
    {
        if (i < 0 || i >= this.vertexCount())
            return;
        boolean[] visited = new boolean[this.vertexCount()]; //访问标记数组，元素初值为false，表示未被访问
        int j = i;
        do {
            if (!visited[j])                     //若顶点vj未被访问
            ////若j序号越界，则Java抛出ArrayIndexOutOfBoundsException数组下标序号越界异常
            {
                System.out.print("{ ");
                this.depthfs(j, visited);        //从顶点vj出发的一次深度优先搜索
                System.out.print("} ");
            }
            j = (j + 1) % this.vertexCount();      //在其他连通分量中寻找未被访问顶点
        } while (j != i);
        System.out.println();
    }
    //第5版，改为如下，不行，循环次数为0。同理，不能改为while()
//      for(int j=i;  j!=i;  j=(j+1) % this.vertexCount())  //在其他连通分量中寻找未被访问顶点
//          if(!visited[j])                      //若顶点vj未被访问。若i越界，Java将抛出数组下标序号越界异常

    //从顶点vi出发的一次深度优先搜索，遍历一个连通分量；visited[]指定访问标记，引用类型。递归算法
    private void depthfs(int i, boolean[] visited) {
//        System.out.print(this.get(i)+" ");       //访问顶点vi
        System.out.print("->" + this.get(i) + " ");  //访问顶点vi////显示路径，相当于入栈
        visited[i] = true;                       //设置访问标记
        //以下循环j依次获得vi的所有邻接顶点序号。
        //next(i,j)返回vi在vj后的后继邻接顶点序号；若j=-1，返回vi的第0个邻接顶点序号；若不存在后继邻接顶点，返回-1
        for (int j = next(i, -1); j != -1; j = next(i, j))
            if (!visited[j])                      //若顶点vj未被访问
            {
                depthfs(j, visited);             //从vj出发的深度优先搜索，递归调用
                System.out.print(this.get(i) + "<- ");       //访问顶点vi，相当于出栈
            }
    }
    //算法结构同树的先根次序遍历（子树递归，兄弟链循环）。


    //§7.3.2   图的广度优先遍历
    public void BFSTraverse(int i)               //图从顶点vi出发的一次广度优先遍历，包含非连通图
    {
        if (i < 0 || i >= this.vertexCount())
            return;
        boolean[] visited = new boolean[this.vertexCount()]; //访问标记数组
        //下句顺序循环队列。为了演示顺序循环队列，容量为顶点数-1；无参数时，默认容量
        Queue<Integer> que = new SeqQueue<Integer>(this.vertexCount() - 1);
//        Queue<Integer> que = new LinkedQueue<Integer>();   //链式队列
        int j = i;
        do {
            if (!visited[j])                      //若顶点vj未被访问
            ////若j序号越界，则Java抛出ArrayIndexOutOfBoundsException数组下标序号越界异常
            {
                System.out.print("{ ");
                breadthfs(j, visited, que);      //从vj出发的一次广度优先搜索
                System.out.print("} ");
            }
            j = (j + 1) % this.vertexCount();      //在其他连通分量中寻找未被访问顶点
        } while (j != i);
        System.out.println();
    }

    //从顶点vi出发的一次广度优先搜索，遍历一个连通分量；共用visited[]和que队列
    private void breadthfs(int i, boolean[] visited, Queue<Integer> que) {
        System.out.print(this.get(i) + " ");       //访问顶点vi
        visited[i] = true;                       //设置访问标记
        que.add(i);                              //访问过的顶点vi序号入队，自动转换成Integer(i))
        ////说明：顶点vi刚入队就出队，作用是使以下循环条件成立。二叉树的层次遍历算法，队列元素是结点，循环条件是p!=null
        while (!que.isEmpty())                    //当队列不空时循环
        {
            System.out.println("顶点队列：" + que.toString());
            i = que.poll();                      //出队，自动转换成int;
            for (int j = next(i, -1); j != -1; j = next(i, j))    //j依次获得vi的所有邻接顶点序号
            {
                if (!visited[j])                  //若顶点vj未访问过，则访问，序号j入队
                {
                    System.out.print(this.get(j) + " ");
                    visited[j] = true;
                    ////说明：试图将上两句写到for循环外，与访问顶点vi两句合并，算法不行。
                    ////因为，其一此处顶点要先访问，再入队，将改变visited[]判断条件。
                    ////其二，此for循环，访问的是vi的所有邻接顶点，不包括vi。
                    que.add(j);
                }
            }
        }
    }
    //说明：上述算法与二叉树的层次遍历算法不同。
    //（1）二叉树的层次遍历算法，结点先入队，出队再访问。
    //    因为，二叉树没有回路，子树不相交，入队结点没有重复的，所以，孩子入队时不需要判断是否访问过。
    //（2）图的广度优先遍历算法，顶点先访问，再入队。
    //    因为，图存在回路，为了保证入队结点没有重复的，所以，入队条件必须是未访问顶点。


    //§7.4   最小生成树
    //Prim算法，构造带权无向图的最小生成树，输出最小生成树的各边及代价
    public void minSpanTree() {
        Triple[] mst = new Triple[vertexCount() - 1];        //最小生成树的边集合，边数为顶点数n-1
        for (int i = 0; i < mst.length; i++)                  //边集合初始化，从顶点v0出发构造
            mst[i] = new Triple(0, i + 1, this.weight(0, i + 1)); //保存从v0到其他各顶点的边

//        TripleComparator comp = new TripleComparator();    //三元组的比较器，按值比较Triple对象大小，见5.2.2节
        for (int i = 0; i < mst.length; i++)        //选择n-1条边，每趟确定一条权值最小的边。一趟选择排序算法
        {
            System.out.print("mst边集合：");
            for (int j = 0; j < mst.length; j++)
                System.out.print(mst[j].toString() + ",");
            System.out.println();

            int min = i;                           //最小权值边的下标
            for (int j = i + 1; j < mst.length; j++)  //在i～n-1范围内，寻找权值最小的边
                if (mst[j].value < mst[min].value)//若存在更小权值的边，则更新min变量
//                if(comp.compare(mst[j], mst[min])<0)//若存在更小权值的边，则更新min变量
                    min = j;                     //保存当前权值最小边的序号

            //以下将权值最小的边（由min记得）交换到第i个元素，表示该边加入TE集合
            Triple edge = mst[min];
            if (min != i) {
                mst[min] = mst[i];
                mst[i] = edge;
            }

            //将i+1～n-1的其他边用权值更小的边替换
            int tv = edge.column;                //刚并入TV的顶点
            for (int j = i + 1; j < mst.length; j++) {
                int v = mst[j].column, w;        //v是原边在V-TV中的终点
                if ((w = weight(tv, v)) < mst[j].value) //若(tv,v)边的权值w更小，则替换
                    mst[j] = new Triple(tv, v, w);
            }
        }

        System.out.print("\n最小生成树的边集合：");
        int mincost = 0;
        for (int i = 0; i < mst.length; i++)        //输出最小生成树的边集合和代价
        {
            System.out.print(mst[i] + " ");
            mincost += mst[i].value;
        }
        System.out.println("，最小代价为" + mincost);
    }
    ////说明：（1）Prim算法，对权值只比较大小，没有相加，所以，MAX_WEIGHT=Integer.MAX_VALUE
    ////（2）用数组mst[]即可，不需要用顺序表。因为，与算法描述一致，清楚简练，没有扩容问题。


    //§7.5   最短路径
    //§7.5.1   非负权值的单源最短路径（Dijkstra算法）
    public void shortestPath(int i)              //求带权图中顶点vi的单源最短路径，Dijkstra算法
    {
        int n = this.vertexCount();              //图的顶点数
        boolean[] S = new boolean[n];            //已求出最短路径的顶点集合，元素为false
        S[i] = true;                             //标记源点vi在集合S中。若i越界，Java抛出序号越界异常
        int[] path = new int[n], dist = new int[n];  //最短路径及长度数组，元素为0
        for (int j = 0; j < n; j++)                   //初始化dist和path数组
        {
            dist[j] = this.weight(i, j);                     //dist最短路径长度
            path[j] = (j != i && dist[j] < MAX_WEIGHT) ? i : -1;//path最短路径的终点的前一个顶点
        }
//        System.out.print("\nvset数组"+toString(vset));
//        System.out.print("\tpath数组"+toString(path));
//        System.out.print("\tdist数组"+toString(dist));

        for (int j = (i + 1) % n; j != i; j = (j + 1) % n)      //寻找从vi到vj的最短路径，vj在V-S集合中
        {
            int min = 0, mindist = MAX_WEIGHT, w;    //求路径长度最小值及其下标
            for (int k = 0; k < n; k++) {
                if (!S[k] && dist[k] < mindist) {
                    mindist = dist[k];           //路径长度最小值
                    min = k;                     //路径长度最小值下标
                }
            }
            if (mindist == MAX_WEIGHT)              //若没有其他最短路径则算法结束； 此语句对非连通图必需
                break;
            S[min] = true;                       //确定一条最短路径的终点min并入集合S
            for (int k = 0; k < n; k++)               //调整从vi到V-S中其他顶点的最短路径及长度
            {
                if (!S[k] && (w = weight(min, k)) < MAX_WEIGHT && dist[min] + w < dist[k]) {
                    dist[k] = dist[min] + w;     //用更短路径替换
                    path[k] = min;               //最短路径经过min顶点
                }
            }
//            System.out.print("\nvset数组"+toString(vset));
//            System.out.print("\tpath数组"+toString(path));
//            System.out.print("\tdist数组"+toString(dist));
        }

        System.out.print(this.get(i) + "的单源最短路径：");
        for (int j = 0; j < n; j++)                   //输出顶点vi的单源最短路径
            if (j != i)
                System.out.print(toPath(path, i, j) + "长度" + (dist[j] == MAX_WEIGHT ? "∞" : dist[j]) + "，");
        System.out.println();
    }
    ////说明：（1）Dijkstra算法，对权值不仅比较大小，还要相加，如果MAX_WEIGHT=Integer.MAX_VALUE，相加后为负值，所以，不能。
    ////（2）用数组S[]、path[]、dist[]即可，不需要用顺序表。因为，与算法描述一致，清楚简练，没有扩容问题。

    private String toPath(int[] path, int i, int j) //返回path路径数组中从顶点vi到vj的一条路径字符串
    {
        SinglyList<T> link = new SinglyList<T>();//单链表，记录最短路径的各顶点
        link.insert(this.get(j));                //单链表插入最短路径终点vj
        for (int k = path[j]; k != i && k != j && k != -1; k = path[k])
            link.insert(0, this.get(k));         //单链表头插入经过的顶点，反序
        link.insert(0, this.get(i));             //最短路径的起点vi
        return link.toString();
    }

    private static String toString(int[] value)  //输出数组值，显示中间结果
    {
        if (value != null && value.length > 0) {
            String str = "{";
            int i = 0;
            for (i = 0; i < value.length - 1; i++)
                str += (value[i] == MAX_WEIGHT ? "∞" : value[i]) + ",";
            return str + (value[i] == MAX_WEIGHT ? "∞" : value[i]) + "}";
        }
        return null;
    }


    @Override
    //§7.5.2   每对顶点间的最短路径（Floyd算法）
    //第5版，用第3版程序，使用int[][]表示D和P矩阵，更清楚，没有改变矩阵行列数。
    //用Dijkstra算法的toPath(path[],i,j)方法
    public void shortestPath()              //求带权图每对顶点间的最短路径及长度，Floyd算法
    {
        int n = this.vertexCount();                //n为图的顶点数
        int[][] path = new int[n][n], dist = new int[n][n];    //最短路径及长度矩阵，元素为0
        for (int i = 0; i < n; i++)                   //初始化dist、path矩阵
        {
            for (int j = 0; j < n; j++) {
                dist[i][j] = this.weight(i, j);   //dist初值是图的邻接矩阵//dist存储每对顶点间的最短路径长度
                path[i][j] = (i != j && dist[i][j] < MAX_WEIGHT) ? i : -1; //终点的前一个顶点序号
            }
        }
        System.out.println("dist：\n" + toString(dist) + "path：\n" + toString(path) + "路径矩阵：");
        printPathAll(path);

        for (int k = 0; k < n; k++)                   //以vk作为其他路径的中间顶点
        {
            System.out.println("\n以" + this.get(k) + "作为中间顶点，替换路径如下：");
            for (int i = 0; i < n; i++)               //测试每对从vi到vj路径长度是否更短
                if (i != k)
                    for (int j = 0; j < n; j++)
                        if (j != k && j != i) {
                            System.out.print(toPath(path[i], i, j) + "路径长度" + dist[i][j] + "，替换为" +
                                    toPath(path[i], i, k) + "," + toPath(path[k], k, j) + "路径长度" + (dist[i][k] + dist[k][j]) + "？");
                            if (j != k && j != i && dist[i][j] > dist[i][k] + dist[k][j])//若更短，则替换
                            {
                                dist[i][j] = dist[i][k] + dist[k][j];
                                path[i][j] = path[k][j];
                                System.out.println("是，d" + i + j + "=" + dist[i][j] + "，p" + i + j + "=" + path[i][j]);
                            } else
                                System.out.println("否");
                        }
            System.out.println("dist：\n" + toString(dist) + "path：\n" + toString(path) + "路径矩阵：");
//            System.out.println("dist"+dist.toString()+"path"+path.toString()+"路径矩阵：");
            printPathAll(path);
        }

        System.out.println("\n每对顶点间的最短路径如下：");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                if (i != j)
                    //下句中toPath(path[i],i,j)返回path路径矩阵中从vi到vj的路径字符串，见Dijkstra算法
                    System.out.print(toPath(path[i], i, j) + "长度" + (dist[i][j] == MAX_WEIGHT ? "∞" : dist[i][j]) + "，");
            System.out.println();
        }
    }
    ////说明：（1）Floyd算法，对权值不仅比较大小，还要相加，如果MAX_WEIGHT=Integer.MAX_VALUE，相加后为负值，所以，不能。
    ////（2）用数组path[][]、dist[][]即可，不需要用矩阵。因为，与算法描述一致，清楚简练，没有扩容问题。
    ////  与Dijkstra算法共用toPath()方法。

    private void printPathAll(int[][] path)      //输出path路径矩阵中每对顶点间的路径字符串
    {
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[i].length; j++)
                System.out.print(toPath(path[i], i, j) + " ");
            System.out.println();
        }
    }

    public static String toString(int[][] value) {
        String str = "";
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++)
                str += value[i][j] == MAX_WEIGHT ? "     ∞" : String.format("%6d", value[i][j]);
            str += "\n";
        }
        return str;
    }

    public boolean equals(Object obj) {
        //比较两个链表中的内容相同，顺序可以不同
        //即两个表长度相同
        if (obj == this) {
            return true;
        }
        if (obj instanceof AbstractGraph) {
            AbstractGraph abstractGraph = (AbstractGraph) obj;
            if (abstractGraph.vertexlist.n != this.vertexlist.n) {
                return false;
            }

            for (int i = 0; i < this.vertexlist.n; i++) {
                //只有每个都找得到才能返回true
                int j;
                for (j = 0; j < this.vertexlist.n; j++) {
                    if (this.vertexlist.element[i].equals(abstractGraph.vertexlist.element[j])) {
                        break;
                    }
                    if (j == this.vertexlist.size()) {
                        return false;
                    }
                }
                //走到这里说明在前面都没找到
//                return false;
            }

            //前面判断的是节点，还需要判断边，边集合待子类去实现

            return true;
        }
        return false;
    }

    public boolean isSubGraph(Graph<T> graph) {
        //图是子图，其实就是判断是不是子链表
        if (graph.vertexCount() > this.vertexCount()) {
            return false;
        }
        int num = graph.vertexCount();
        for (int i = 0; i < num; i++) {
            int find = search(get(i));
            if (find != -1) {
                return false;
            }
        }

        return true;
    }

    //判断生成子图
    public boolean isSpanSubGraph(Graph<T> graph) {
        if (graph == this) {
            return true;
        }
        if (graph instanceof AbstractGraph) {
            AbstractGraph abstractGraph = (AbstractGraph) graph;
            if (abstractGraph.vertexlist.n != this.vertexlist.n) {
                return false;
            }

            for (int i = 0; i < this.vertexlist.n; i++) {
                //只有每个都找得到才能返回true
                int j;
                for (j = 0; j < this.vertexlist.n; j++) {
                    if (this.vertexlist.element[i].equals(abstractGraph.vertexlist.element[j])) {
                        break;
                    }
                    if (j == this.vertexlist.size()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    static int vcount = 0;

    public boolean isTree() {
        //访问标记数组，元素初值为false
        boolean[] visited = new boolean[this.vertexCount()];
        //father为遍历顶点的父顶点，起点无父顶点，令初值为-1
        int father = -1;
        //只遍历一条连通分量，若遍历完vcount=图总顶点数，则为连通图
        boolean result = this.depth(0, visited, father);//result判断是否有回路
        if (vcount == this.vertexCount()) //判断是否连通
        {
            vcount = 0;
            return result;
        }
        vcount = 0;
        return false;
    }

    private boolean depth(int i, boolean[] visited, int father) {
        visited[i] = true;              //设置访问标记
        vcount++;                       //遍历过的顶点数加1
        //j依次获得vi的所有邻接顶点序号
        for (int j = this.next(i, -1); j != -1; j = this.next(i, j)) {
            if (!visited[j])         //若邻接顶点vj未被访问
            {
                father = i;            //vi为vj的父顶点
                if (!depth(j, visited, father))
                    return false;
            } else if (j != father)      //邻接顶点vj已被访问，且不是vi的父顶点，则有回路
                return false;
        }
        return true;
    }
}

