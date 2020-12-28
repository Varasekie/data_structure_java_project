package EigthChar;

public class BinarySortTree_Integer {
    public static void main(String[] args)
    {
        //§1.1.3  数据类型与抽象数据类型
//        Set<Integer> set = new BinarySortTree<Integer>();  //Set<T>接口对象set引用BinarySortTree<T>类的实例，赋值相容
//        Integer x = new Integer(100);
//        set.add(x);                              //运行时多态性，执行BinarySortTree<T>类实现的add(x)方法
//        System.out.println("set="+set.toString());


    	//图8.12～图8.16，插入、查找
        Integer[] values={54,18,81,99,36,12,12,76,57,6,66};  //图8.12（a）
        BinarySortTree<Integer> bstree=new BinarySortTree<Integer>(values); //构造二叉排序树
//        System.out.print("图8.12，");
//        bstree.inorder();                        //中根次序遍历二叉排序树，输出按关键字升序排列的元素序列
////        bstree.inorderPrevious();
//        bstree.preorder();                       //先根次序迭代遍历二叉排序树，说明二叉树的形态
//        bstree.printASL();
//
//        Integer key = 57;
////        System.out.println("查找"+key+"，"+(bstree.search(key)!=null?"":"不")+"成功 ");
////        key = 40;
////        System.out.println("查找"+key+"，"+(bstree.search(key)!=null?"":"不")+"成功 ");
//
//        System.out.print("\n图8.13，图8.17，");
//        System.out.print("插入"+key+"，");
//        bstree.add(key);                         //插入40，图8.13，图8.17
//        bstree.inorder();                        //中根次序遍历二叉排序树
//        bstree.preorder();                       //先根次序迭代遍历二叉排序树，说明二叉树的形态
//        bstree.printASL();
//
//        Integer[] delete={12,36,values[0]};      //图8.16(a)(b)，删除1度结点；(c)，删除根，2度结点
//        System.out.print("\n图8.16，删除");
//        for(Integer k : delete)
//            System.out.print(bstree.remove(k)+"，");
//        key = values[0];
//        System.out.print("插入"+key+"，");
//        bstree.add(key);
//        bstree.inorder();
//        bstree.printASL();

//        while(bstree.root!=null)                 //删除根，删除全部结点
//        {
//            System.out.print("删除"+bstree.removeRoot()+"，");
//            bstree.inorder();
//        }

    }
}
