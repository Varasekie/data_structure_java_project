package Last;

public class test {
    public static void main(String[] args) {
        Triple[] triples = new Triple[]{
                new Triple(0, 1, 25),
                new Triple(0, 3, 12),
                new Triple(1, 2, 37),
                new Triple(1, 3, 46),
                new Triple(2, 3, 6),
        };


        Triple[] triples_linkmat = new Triple[]{
                new Triple(0,3,10),
                new Triple(0,1,45),
                new Triple(0,2,28),
                new Triple(1,2,12),
                new Triple(2,3,15),
                new Triple(1,4,21),
                new Triple(2,4,26),
                new Triple(3,4,15),
                new Triple(3,5,13),
                new Triple(4,5,11)
        };

        //构造
        Linkmat linkmat = new Linkmat(triples, 4);
        //插入就是在构造的时候实现的
//        System.out.println(linkmat.toString());
//        linkmat.BFS();
//        System.out.println(linkmat.search(2,3).toString());;
        //删除某一个
//        linkmat.delete(new Triple(0, 1, 25));
//        linkmat.delete(new Triple(1,3,46));
//        System.out.println(linkmat.toString());;
//        linkmat.BFS();
        //求Prim
//        linkmat.Prim();

        Linkmat linkmat1 = new Linkmat(triples_linkmat,6);
//        linkmat1.minSpanTree();

        //下面是有向图
//        主要是不想优化了，这个n其实可以通过遍历求最大值得到的，但是还不如自己输入，如果后续有要求可以写个函数直接调用……也就一个for循环
        Triple[] triples1 = new Triple[]{
                new Triple(0, 1, 25),
                new Triple(0, 3, 12),
                new Triple(2, 1, 37),
                new Triple(1, 3, 46),
                new Triple(2, 3, 58),
                new Triple(3, 1, 46)
        };
        DirectedLinkedMat directedLinkedMat = new DirectedLinkedMat(triples1,4);
////        System.out.println(directedLinkedMat.toString());
////        directedLinkedMat.BFS();
        //删除两个节点
////        directedLinkedMat.delete(new Triple(2, 1, 37));
//        directedLinkedMat.delete(new Triple(0, 1, 25));
//        directedLinkedMat.BFS();
        //两个测试
//        directedLinkedMat.Dijkstra(0);
//        directedLinkedMat.Dijkstra(2);


        //单源最短路径测试
        Triple[] triples2 = new Triple[]{
                new Triple(0,1,10),
                new Triple(1,2,50),
                new Triple(0,4,99),
                new Triple(0,3,30),
                new Triple(1,3,40),
                new Triple(2,4,10),
                new Triple(3,2,20),
                new Triple(3,4,60)
        };
        DirectedLinkedMat linkedMat = new DirectedLinkedMat(triples2,5);
//        linkedMat.Dijkstra(0);

        linkedMat.delete(new Triple(3,2,60));
        //删除
//        linkedMat.delete(0);
//        linkedMat.delete_test(0);
        linkedMat.BFS();
//        System.out.println(linkedMat.toString());;
    }
}
