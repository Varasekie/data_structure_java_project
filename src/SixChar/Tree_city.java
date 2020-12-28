package SixChar;

public class Tree_city {
    static String[] prelists = {
            "中国", "\t北京", "\t江苏", "\t\t南京", "\t\t苏州", "\t浙江", "\t\t杭州", "韩国", "\t首尔"}; //2,【例6.3】 图6.26，城市树/森林

    public static void main(String[] args) {
        Tree<String> tree1 = Trees.create(prelists);    //以树的横向凹入表示法构造树/森林
//        Tree<String> tree2 = new Tree<String>(tree1);      //深拷贝
        tree1.preorder();                                  //输出树的先根次序遍历序列
        tree1.preorder2();                                 //输出树的先根次序遍历序列
        tree1.postorder();                                 //输出树的后根次序遍历序列
        System.out.print(tree1.toString());                //先根次序遍历树并输出树的横向凹入表示字符串
    }
}
