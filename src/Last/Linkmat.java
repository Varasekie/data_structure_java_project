package Last;

import SixChar.BinaryTree;

//这个是无向图的实现，不需要前后节点混在一起
public class Linkmat<T> {
//    顺序表其实本质就是数组啊

    Vertex<Triple>[] vertexs;

    SeqList<Vertex<Triple>> list;
//    = new SeqList<Vertex<Triple>>(vertexs);

    int MAXWEIGHT = 99999;

    public Linkmat() {
        this(0);
    }


    public Linkmat(int n) {
//        this(n, n);

        this.vertexs = new Vertex[n];
        //然后存进顺序表
        list = new SeqList<>(n);

        for (int i = 0; i < n; i++) {
            this.vertexs[i] = new Vertex<>();
            this.vertexs[i].ver = i;
            this.list.insert(vertexs[i]);
        }
    }

    //删除某个边

    public void delete(Triple triple) {
        //测试，如果不用list直接做
        //……出bug了
        DoubleNode<Triple> find_out = this.vertexs[triple.row].head.next;
        //删除头节点
        if (find_out.data.equals(triple)) {
            this.vertexs[triple.row].head.next = this.vertexs[triple.row].head.next.next;
        }

        while (find_out.next != null && find_out.next.data.column > triple.column) {
            if (find_out.next.data.equals(triple)) {
                find_out.next = find_out.next.next;
                break;
            }

            find_out = find_out.next;
        }

        while (find_out.down != null && find_out.down.data.row < triple.row) {
            if (find_out.down.data.equals(triple)) {
                find_out.down = find_out.down.down;
                break;
            }
            find_out = find_out.down;
        }

        DoubleNode<Triple> find_in = this.list.get(triple.column).head.next;
        if (find_in.data.equals(triple)) {
            this.vertexs[triple.column].head.next = find_in.down;
        }
        while (find_in.down != null) {
            if (find_in.down.data.equals(triple)) {
                find_in.down = find_in.down.down;
            }
            find_in = find_in.down;
        }
    }

    public void insert(Triple triple) {
        DoubleNode<Triple> node = new DoubleNode<Triple>(triple);
        DoubleNode<Triple> find_before = this.list.get(triple.row).head.next;
        DoubleNode<Triple> find_after = this.list.get(triple.column).head.next;
        //首先要保证，顶点后面如果没点怎么办，那就直接接在后面
        //要保证两个都这样了x
        if (find_after == null && find_before == null) {
            this.list.get(triple.row).head.next = node;
            this.list.get(triple.column).head.next = node;
            return;
        }

        //m用来判断哪个是已经里面有节点的,还有可能是两个都是已经有节点的
        int m = -1;
        if (find_after == null) {
            m = triple.row;
            this.list.get(triple.column).head.next = node;
        }
        if (find_before == null) {
            m = triple.column;
            this.list.get(triple.row).head.next = node;
        }
        //m用来判断哪个是已经里面有节点的
        if (m != -1) {
            insert_test(this.list.get(m).head.next, triple, node);
        } else {
            insert_test(find_before, triple, node);
            insert_test(find_after, triple, node);
        }
    }

    //这个是具体的插入函数
    private void insert_test(DoubleNode<Triple> find, Triple triple, DoubleNode<Triple> node) {
        //头插入，没有也行
//        if (triple.row == find.data.row && triple.column < find.data.column) {
//            node.next = find;
//            find = node;
//        }

        //寻找匹配的
        if (find.data.row < triple.row) {
            while (find.down != null && find.data.row < triple.row) {
                find = find.down;
            }
            if (find.data.row == triple.row) {
                while (find.next != null) {
                    find = find.next;
                }
                find.next = node;
            } else
                find.down = node;
        } else {
            while (find.next != null) {
                find = find.next;
            }
            find.next = node;
        }
    }


    //构造函数，调用insert
    public Linkmat(Triple[] triples, int n) {
        this(n);
        //往里添加节点
        for (Triple triple : triples) {
            //先容错
            if (triple.column >= n || triple.row >= n) {
                throw new IllegalArgumentException("vertex数量错误");
            }
        }
        for (Triple triple : triples) {
            insert(triple);
        }
    }

    //遍历，广度优先
    public void BFS() {
        SeqQueue<DoubleNode<Triple>> queue = new SeqQueue<DoubleNode<Triple>>();

        //改成循环遍历顺序表
        for (int i = 0; i < list.size(); i++) {
            Vertex<Triple> vertex = list.get(i);
            if (vertex.head.next == null) {
                continue;
            }
            if (!vertex.head.next.visited) {
                queue.add(vertex.head.next);
                vertex.head.next.visited = true;
            }
//        System.out.println(queue.peek());;
            while (!queue.isEmpty()) {
                //next入栈
                if (queue.peek().next != null && !queue.peek().next.visited) {
                    queue.add(queue.peek().next);
                    queue.peek().next.visited = true;

                }
                //down入栈
                if (queue.peek().down != null && !queue.peek().down.visited) {
                    queue.add(queue.peek().down);
                    queue.peek().down.visited = true;
                }
                System.out.println(queue.peek());
                queue.poll();
            }

        }
    }

    //由于bfs改变了其中的visited，还需要写一个返回去的函数x
    public void BFS_reverse() {
        //之后再让其都变回1
        //默认从A开始遍历
        SeqQueue<DoubleNode<Triple>> queue = new SeqQueue<DoubleNode<Triple>>();
        //先入栈，down和next
        queue.add(this.vertexs[0].head.next);
//        System.out.println(queue.peek());;
        while (!queue.isEmpty()) {
//            System.out.println(queue.peek());
            if (queue.peek().next != null && queue.peek().next.visited) {
                queue.add(queue.peek().next);
                queue.peek().next.visited = false;

            }
            if (queue.peek().down != null && queue.peek().down.visited) {
                queue.add(queue.peek().down);
                queue.peek().down.visited = false;
            }
            queue.poll();
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        SeqQueue<DoubleNode<Triple>> queue = new SeqQueue<DoubleNode<Triple>>();
        //先入栈，down和next

        queue.add(this.vertexs[0].head.next);
        this.vertexs[0].head.next.visited = true;
//        System.out.println(queue.peek());;
        while (!queue.isEmpty()) {
            System.out.println(queue.peek());
            string.append(queue.peek().toString());
            if (queue.peek().next != null && !queue.peek().next.visited) {
                queue.add(queue.peek().next);
                queue.peek().next.visited = true;

            }
            if (queue.peek().down != null && !queue.peek().down.visited) {
                queue.add(queue.peek().down);
                queue.peek().down.visited = true;
            }
            queue.poll();
        }
        this.BFS_reverse();
        return string.toString();
    }


    public void minSpanTree()
    {
        Triple[] mst = new Triple[vertexs.length-1];        //最小生成树的边集合，边数为顶点数n-1
        for (int i=0; i<mst.length; i++)                   //边集合初始化，从顶点v0出发构造
            mst[i]=new Triple(0,i+1,this.weight(0,i+1));   //保存从v0到其他各顶点的边

        for (int i=0; i<mst.length; i++)                   //选择n-1条边，每趟确定一条权值最小的边。一趟选择排序算法
        {
            System.out.print("mst边集合：");
            for(int j=0; j<mst.length; j++)
                System.out.print(mst[j].toString()+",");
            System.out.println();

            int min=i;         //最小权值及边的下标
            for (int j=i+1; j<mst.length; j++)             //在i～n-1范围内，寻找权值最小的边
                if (mst[j].value < mst[min].value)//minweight)              //若存在更小权值，则更新最小值变量
                {
//                    minweight = mst[j].value;              //最小权值
                    min = j;                               //保存当前权值最小边的序号
                }

            //将权值最小的边（由min记得）交换到第i个元素，表示该边加入TE集合
            Triple edge = mst[min];
            if (min!=i)
            {
                mst[min] = mst[i];
                mst[i] = edge;
            }

            //将i+1～n-1的其他边用权值更小的边替换
            int tv = edge.column;                          //刚并入TV的顶点
            for (int j=i+1; j<mst.length; j++)
            {
                int v = mst[j].column;                     //原边在V-TV中的终点
                int weight = this.weight(tv,v);
                if (weight<mst[j].value)                   //若(tv,v)边比第j条边的权值更小，则替换
                    mst[j] = new Triple(tv,v,weight);
            }
        }

        System.out.print("\n最小生成树的边集合：");
        int mincost=0;
        for (int i=0; i<mst.length; i++)                   //输出最小生成树的边集合和代价
        {
            System.out.print(mst[i]+" ");
            mincost += mst[i].value;
        }
        System.out.println("，最小代价为"+mincost);
    }

    public void Prim() {
        Triple[] mst = new Triple[this.list.size() - 1];
        //保存v0到其他顶点的边
        DoubleNode<Triple> node = this.list.get(0).head.next;
        int i = 0;
        while (i < mst.length && node != null) {
            if (node.data.column > i + 1) {
                mst[i] = new Triple(0, i + 1, MAXWEIGHT);
            } else {
                mst[i] = node.data;
                node = node.next;
            }
            i++;
        }

        //一趟排序算法，这里就是先选一条最短的边，然后再把每个点到这条边的距离更新了，然后再找一条小边（其实没做到怎么
        for (i = 0; i < mst.length; i++) {
            System.out.print("mst边集合：");
            for (Triple triple : mst) System.out.print(triple.toString() + ",");
            System.out.println();
            int min = i;
            for (int j = 0; j < mst.length; j++) {
                if (mst[j].value < mst[min].value) {
                    min = j;
                }
            }

            //把边并入
            Triple edge = mst[min];
            if (min != i) {
                mst[min] = mst[i];
                mst[i] = edge;
            }

            //权值最小的边放到第i个位置
            int tv = edge.column;//tv相当于入边
            for (int j = i + 1; j < mst.length; j++) {
                int v = mst[j].column;
                int weight;
                //如果权值更小，则直接替换。

                if ((weight = weight(tv, v)) < mst[j].value) {
                    mst[j] = new Triple(tv, v, weight);
                }

            }
        }
        System.out.println();
    }

    //获得权重（方法：遍历找到某个节点，然后获取value
    public int weight(int i, int j) {
        if (i == j) {
            return 0;
        }
        int w;
        //然后遍历搜索找节点,BFS
        DoubleNode<Triple> doubleNode = this.search(i, j);
        if (doubleNode != null) {
            return doubleNode.data.value;
        }
        return MAXWEIGHT;
    }

    public static void main(String[] args) {
        System.out.println();
    }

    public DoubleNode<Triple> search(int i, int j) {
        //深度优先搜索
        SeqQueue<DoubleNode<Triple>> queue = new SeqQueue<DoubleNode<Triple>>();
        //先入栈，down和next

        //保证i《j，因为是无向图才需要（有向图不需要
        if (j > i) {
            int temp = j;
            j = i;
            i = temp;
        }
        queue.add(this.vertexs[0].head.next);
//        System.out.println(queue.peek());;
        while (!queue.isEmpty()) {
            if ((queue.peek().data.row == i && queue.peek().data.column == j) || (queue.peek().data.column == i && queue.peek().data.row == j)) {
                return queue.peek();
            }
            //把next和down都放进来
//            System.out.println(queue.peek());
//            string.append(queue.peek().toString());
            if (queue.peek().next != null && !queue.peek().next.visited) {
                queue.add(queue.peek().next);
                queue.peek().next.visited = true;
            }
            if (queue.peek().down != null && !queue.peek().down.visited) {
                queue.add(queue.peek().down);
                queue.peek().down.visited = true;
            }
            queue.poll();
        }
        this.BFS_reverse();
        return null;
    }
}
