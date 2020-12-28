package Last;

import java.util.Arrays;

public class DirectedLinkedMat extends AbstractGraph {
    //入边和出边
    Vertex<Triple>[] in, out;

    int m =-1;
    public DirectedLinkedMat() {
        this(0);
    }


    public DirectedLinkedMat(int n) {
//        this(n, n);
        this.in = new Vertex[n];
        this.out = new Vertex[n];
        for (int i = 0; i < n; i++) {
            this.in[i] = new Vertex<>();
            this.out[i] = new Vertex<>();
            this.in[i].ver = i;
            this.out[i].ver = i;
        }
    }

    public void delete(Triple triple) {
        //如果要删除头节点，直接找出边入边，然后
        DoubleNode<Triple> find_out = this.out[triple.row].head.next;

        //反正按道理肯定是能找到的，找不到就是它的问题了
        //删除next
        if (find_out.data.row == triple.row && find_out.data.column == triple.column) {
            this.out[triple.row].head.next = this.out[triple.row].head.next.next;
        }
        while (find_out.next != null) {
            if (find_out.data.row == triple.row && find_out.data.column == triple.column) {
                //新增往下指
                if (find_out.next.next == null && find_out.next.down != null) {
                    this.in[triple.column].head.next = find_out.next.down;
                } else find_out.next = find_out.next.next;
                break;
            }
            find_out = find_out.next;
        }

        //删除down
        //这里是需要管down的，因为这里向下的肯定是相同的入边，down是继承的
        DoubleNode<Triple> find_in = this.in[triple.column].head.next;
        if (find_in.data.row == triple.row && find_in.data.column == triple.column) {
            this.in[triple.column].head.next = find_in.down;
        }
        while (find_in.down != null) {
            if (find_in.data.row == triple.row && find_in.data.column == triple.column) {
                find_in.down = find_in.down.down;
            }
            find_in = find_in.down;
        }
    }

    //删除节点，遍历删除边,两次遍历
    public void delete(int i) {
        DoubleNode<Triple> find_out = this.out[i].head.next;
        //up不会为null(除非前面删掉了orz
        DoubleNode<Triple> up = this.in[i].head.next;
        //反正按道理肯定是能找到的，找不到就是它的问题了
        //删除next

        //先删后面的，在删头节点
        while (find_out.next != null) {
            //删行的时候考虑列
            //接下来要把上面指向的往下指
            //考虑要有up的或者没有up的
            if (up != null) {
                while (up.down != null) {
                    if (up.down.data.row == i) {
                        if (up.down.down != null) {
                            up.down = up.down.down;
                        } else up.down = null;
                    }
                    up = up.down;
                }
            } else {
                //还是只删某一个
                find_out.next = find_out.next.next;
//                break;
                continue;
            }
            find_out = find_out.next;
        }
        //删头节点
        this.out[i].head.next = null;
        //删除down
        //入边指的直接删掉就行了
        DoubleNode<Triple> find_in = this.in[i].head.next;
        //入边删除所有的down

        if (find_in != null && find_in.down != null) {
            find_in.down = null;
        }
        for (int j = 0; j < in.length; j++) {
            delete(new Triple(i, j, 0));
            delete(new Triple(j, i, 0));
        }
        this.m = i;
    }

    public void delete_test(int i) {
        SeqQueue<DoubleNode<Triple>> queue = new SeqQueue<DoubleNode<Triple>>();
        //先入栈，down和next
        for (Vertex<Triple> tripleVertex : in) {
            if (tripleVertex.head.next == null) {
                continue;
            }
            queue.add(tripleVertex.head.next);

            while (!queue.isEmpty()) {
                if (queue.peek().data.row != i && queue.peek().data.column != i) {
                    System.out.println(queue.peek());
                }
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
                queue.poll();
            }
        }
    }

    public void insert(Triple triple) {
        DoubleNode<Triple> node = new DoubleNode<Triple>(triple);
        //先判断出边，就是row
        DoubleNode<Triple> find_out = this.out[triple.row].head.next;
        if (find_out == null) {
            this.out[triple.row].head.next = node;
        } else {
            while (find_out.next != null) {
                find_out = find_out.next;
            }
            find_out.next = node;
        }

        //判断入边
        DoubleNode<Triple> find_in = this.in[triple.column].head.next;
        if (find_in == null) {
            this.in[triple.column].head.next = node;
        } else {
            while (find_in.down != null) {
                find_in = find_in.down;
            }
            find_in.down = node;
        }


    }

    //广度优先
    public void BFS() {
        //其实只需要遍历in或者out的某一个数组就可以了……其他的和无向图是一样的
        SeqQueue<DoubleNode<Triple>> queue = new SeqQueue<DoubleNode<Triple>>();
        //先入栈，down和next
        for (Vertex<Triple> tripleVertex : in) {
            if (tripleVertex.head.next == null) {
                continue;
            }
            queue.add(tripleVertex.head.next);
//        System.out.println(queue.peek());;
            while (!queue.isEmpty()) {
                System.out.println(queue.peek());
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
                queue.poll();
            }

        }

        //问题是这里还需要另一条边，有可能没有被遍历到
//        this.BFS_reverse();

    }

    public String strings(int m) {
        StringBuilder str = new StringBuilder();
        int i;
        for (i = 0; i < this.out.length; i++) {
            if (this.out[i].head.next != null) {
                break;
            }
        }

        //默认从A开始遍历
        SeqQueue<DoubleNode<Triple>> queue = new SeqQueue<DoubleNode<Triple>>();
        //先入栈，down和next
        queue.add(this.in[i].head.next);
//        this.in[i].head.next.visited = true;
//        System.out.println(queue.peek());;
        while (!queue.isEmpty()) {
            if (m != -1){
                if (queue.peek().data.row != m || queue.peek().data.column != m) {
                    System.out.println(queue.peek());
//                continue;
                }
            }

            if (queue.peek().next != null && !queue.peek().next.visited) {
                queue.add(queue.peek().next);
                queue.peek().next.visited = true;

            }
            if (queue.peek().down != null && !queue.peek().down.visited) {
                queue.add(queue.peek().down);
                queue.peek().down.visited = true;
            }
            str.append(queue.peek().data.toString());
            queue.poll();
        }
        this.BFS_reverse();
        return str.toString();
    }

    public String toString() {

        return strings(m);
    }

    int MAXWEIGHT = 99999;

    public DirectedLinkedMat(Triple[] triples, int n) {
        this(n);
        for (Triple triple : triples) {
            //先容错
            if (triple.column >= n || triple.row >= n) {
                throw new IllegalArgumentException("点数量错误");
            }
        }

        for (Triple triple : triples) {
            insert(triple);
        }
    }

    public void BFS_reverse() {
        //之后再让其都变回1
        //默认从A开始遍历
        SeqQueue<DoubleNode<Triple>> queue = new SeqQueue<DoubleNode<Triple>>();
        //先入栈，down和next
        int i;
        for (i = 0; i < this.in.length; i++) {
            if (this.in[i].head.next != null) {
                break;
            }
        }
        queue.add(this.in[i].head.next);
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

    //Dijkstra算法
    //这里就不用visited的属性改变了，直接使用数组来存储是否已经遍历过了
    public void Dijkstra(int i) {
        int n = this.in.length;
        boolean[] S = new boolean[n];
        S[i] = true;
        int[] path = new int[n], dist = new int[n];
        for (int j = 0; j < n; j++) {
            dist[j] = this.weight(i, j);
            path[j] = (j != i && dist[j] < MAXWEIGHT) ? i : -1;
        }


        //防止越界
        for (int j = (i + 1) % n; j != i; j = (j + 1) % n) {      //寻找从vi到vj的最短路径，vj在V-S集合中
            int min = 0, mindist = MAXWEIGHT, w;    //求路径长度最小值及其下标
            for (int k = 0; k < n; k++) {
                if (!S[k] && dist[k] < mindist) {
                    mindist = dist[k];           //路径长度最小值
                    min = k;                     //路径长度最小值下标
                }
            }
            if (mindist == MAXWEIGHT)              //非连通图必需
                break;
            S[min] = true;                       //确定一条最短路径的终点min并入集合S
            for (int k = 0; k < n; k++) {       //调整从vi到V-S中其他顶点的最短路径及长度
//                System.out.println(w = weight(min, k));
                if (!S[k] && (w = weight(min, k)) < MAXWEIGHT) {
                    //需要有两个更新，如果要更新路径，就直接更新，如果不更新，就直接并入这个集合
                    if (dist[min] + w < dist[k]) {
                        dist[k] = dist[min] + w;     //用更短路径替换
                        path[k] = min;               //最短路径经过min顶点
                    } else {
                        path[k] = min;
                    }
                }
            }
        }

        System.out.print(this.get(i) + "的单源最短路径：");
        for (int j = 0; j < n; j++)                   //输出顶点vi的单源最短路径
            if (j != i)
                System.out.print(toPath(path, i, j) + "长度" + (dist[j] == MAXWEIGHT ? "∞" : dist[j]) + "，");
        System.out.println();
    }

    public DoubleNode<Triple> get(int i)                          //返回顶点vi元素；若i越界，则返回null
    {
        if (this.in[i].head.next != null) {
            return this.in[i].head.next;
        } else return this.out[i].head.next;
    }////遍历用

    @Override
    public int insert(Object x) {
        if (x instanceof Triple) {
            this.insert((Triple) x);
            return ((Triple) x).value;
        }
        return -1;
    }

    @Override
    public void insert(int i, int j, int w) {
        this.insert(new Triple(i, j, w));
    }

    @Override
    public Object remove(int i) {

        return null;
    }

    @Override
    public void remove(int i, int j) {
        this.delete(new Triple(i, j, 0));
    }

    //这里不实现……因为有两个后继节点
    @Override
    protected int next(int i, int j) {
        return 0;
    }

    //用来描述
    private String toPath(int[] path, int i, int j) //返回path路径数组中从顶点vi到vj的一条路径字符串
    {
        //这里的单链表应该和别的数据结构不冲突
        SinglyList<DoubleNode<Triple>> link = new SinglyList<DoubleNode<Triple>>();//单链表，记录最短路径的各顶点
        link.insert(this.get(j));                //单链表插入最短路径终点vj
        for (int k = path[j]; k != i && k != j && k != -1; k = path[k])
            link.insert(0, this.get(k));         //单链表头插入经过的顶点，反序
        link.insert(0, this.get(i));             //最短路径的起点vi
        return link.toString();
    }

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


    public DoubleNode<Triple> search(int i, int j) {
        //广度优先搜索
        SeqQueue<DoubleNode<Triple>> queue = new SeqQueue<DoubleNode<Triple>>();
        //i肯定是出边，j肯定是入边

        queue.add(this.out[i].head.next);
        while (!queue.isEmpty()) {
            if (queue.peek().data.row == i && queue.peek().data.column == j) {
                return queue.peek();
            }
            if (queue.peek().next != null && !queue.peek().next.visited) {
                queue.add(queue.peek().next);
//                queue.peek().next.visited = true;
            }
            if (queue.peek().down != null && !queue.peek().down.visited) {
                queue.add(queue.peek().down);
//                queue.peek().down.visited = true;
            }
            queue.poll();
        }
//        this.BFS_reverse();
        return null;
    }
}
