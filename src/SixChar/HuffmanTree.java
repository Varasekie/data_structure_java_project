package SixChar;

import java.util.HashMap;

public class HuffmanTree {
    private String charset;                      //字符集合
    private TriElement[] element;                //静态三叉链表结点数组

    //构造Huffman树，weights指定权值集合，默认字符集合是从'A'开始的weights.length个字符
    public HuffmanTree(int[] weights)
    {
        this.charset = "";
        int n = weights.length;                            //叶子结点数
        for(int i=0; i<n; i++)                             //默认字符集合是从'A'开始的n个字符
            this.charset += (char)('A'+i);

        this.element = new TriElement[2*n-1];              //n个叶子的Huffman树共有2n-1个结点
        for(int i=0; i<n; i++)                             //Huffman树初始化n个叶子结点
            this.element[i] = new TriElement(weights[i]);  //构造无父母的叶子结点

        for(int i=n; i<2*n-1; i++)                         //构造n-1个2度结点
        {
            int min1=Integer.MAX_VALUE, min2=min1;         //最小和次小权值，初值为整数最大值
            int x1=-1, x2=-1;                              //最小和次小权值结点下标
            for(int j=0; j<i; j++)                         //寻找两个无父母的最小权值结点下标
                if(this.element[j].parent==-1)             //第j个结点无父母
                {
                    if(this.element[j].data<min1)          //第j个结点权值最小
                    {
                        min2 = min1;                       //min2记得次小权值
                        x2 = x1;                           //x2记得次小权值结点下标
                        min1 = this.element[j].data;       //min1记得最小权值
                        x1 = j;                            //x1记得最小权值结点下标
                    }
                    else if(this.element[j].data<min2)     //第j个结点权值次小
                    {
                        min2 = element[j].data;
                        x2 = j;
                    }
                }

            this.element[x1].parent = i;         //合并两棵权值最小的子树，左孩子最小
            this.element[x2].parent = i;
            this.element[i] = new TriElement(min1+min2, -1, x1, x2); //构造结点，指定值、父母、左右孩子
        }
    }

    private String huffmanCode(int i)            //返回charset第i个字符的Huffman编码字符串
    {
        int n=8;
        char code[] = new char[n];               //声明字符数组暂存Huffman编码
        int child=i, parent=this.element[child].parent;
        for(i=n-1; parent!=-1; i--)              //由叶结点向上直到根结点，反序存储编码
        {
            code[i]=(element[parent].left==child)?'0':'1'; //左、右孩子编码为0、1
            child = parent;
            parent = element[child].parent;
        }
        return new String(code, i+1, n-1-i);     //由code数组从i+1开始的n-1-i个字符构造串
    }

    public String toString()                     //返回Huffman树的结点数组和所有字符的编码字符串
    {
        String str="Huffman树的结点数组:";
        for(int i=0; i<this.element.length; i++)
            str += this.element[i].toString()+"，";
        str += "\nHuffman编码： ";
        for(int i=0; i<this.charset.length(); i++)         //输出所有叶子结点的Huffman编码
            str+=this.charset.charAt(i)+"："+huffmanCode(i)+"，";
        return str;
    }//此方法，对于任意字符集合，都可输出编码。
    //以上构造Huffman树，不需要字符集合。

    //数据压缩，将text各字符转换成Huffman编码存储，返回压缩字符串
    public String encode(String text)
    {
        String compressed="";                    //被压缩的数据，以字符串显示
        for(int i=0; i<text.length(); i++)
        {
//            int j=text.charAt(i)-'A';  //获得当前字符在默认字符集（从A开始的n个字符）中的序号，O(1)
            int j=this.map.get(text.charAt(i)+"").intValue(); //第8章，获得当前字符的序号，O(1)
            compressed += this.huffmanCode(j);   //在Huffman树中获得第j个字符的编码
        }
        return compressed;
    }
    //说明：
    //（1）设置默认字符集合的目的是，为字符集合设置一个计算公式，使得由字符查找序号的计算时间是O(1)。
    //（2）对于任意字符集合，压缩时，已知一个字符，如何知道该字符在字符集合中的序号？
    //    解决，第8章，使用散列映射，建立从字符到序号一对一的映射。

    //数据解压缩，将压缩compressed中的0/1序列进行Huffman译码，返回译码字符串
    public String decode(String compressed)
    {
        String text="";
        int node=this.element.length-1;          //node搜索一条从根到达叶子的路径
        for(int i=0; i<compressed.length(); i++)
        {
            if(compressed.charAt(i)=='0')        //根据0、1分别向左或右孩子走
                node = element[node].left;
            else
                node = element[node].right;
            if(element[node].isLeaf())           //到达叶子结点
            {
                text += this.charset.charAt(node);    //已知字符序号，获得一个字符，O(1)
                node = this.element.length-1;    //node再从根结点开始
            }
        }
        return text;
    }
    //说明：试图将压缩与解压缩方法与HuffmanTree类分离。因为，调用者不创建HuffmanTree树。采用Huffman编码，调用者看不到。
    //     未成功。没有办法，只译码，返回字符序号。因为，变长编码，参数不知长度，无法独立；且使用HuffmanTree树的结点数组，私有成员。
    //所以，仍然要用字符集合。

    //第5版改进，第8章增加功能
    java.util.Map<String, Integer> map;          //第8章，映射，散表映射
    //构造Huffman树，charset指定字符集合；weights[]指定权值集合，数组长度为叶子结点数
    public HuffmanTree(String charset, int[] weights)
    {
        this(weights);                           //构造Huffman树，默认字符集集合（从'A'开始的weights.length个字符）
        this.charset = charset;                  //指定字符集合

        //建立从字符到字符序号的一对一映射，使得由字符查找到序号的计算时间是O(1)。
        //映射<字符,字符序号>
        this.map = new HashMap<String, Integer>();
        for(int i=0; i<this.charset.length(); i++)    //输出所有叶子结点的Huffman编码
            map.put(this.charset.charAt(i)+"", i);    //映射元素<第i个字符, 字符序号i>
        System.out.print(map.toString());
    }
    //说明：若指定字符集合，则默认字符集集合无效。其后算法相同，无法识别是哪个字符集合。

    public static void main(String[] args)
    {
        String text="AAAABBBCDDBBAAA";           //【典型案例6.3】 数据
        //…………  统计字符串text的字符集合charset和权值集合weights，见8.5.2节例8.3；

        String charset="ABCD";                   //字符集合
        int[] weight6_29={7,5,1,2};              //图6.33指定权值集合，默认字符集为"ABCD"
//        HuffmanTree huftree = new HuffmanTree(weight6_28); //构造Huffman树

//        String charset="DCBA";                 //字符集合
//        int[] weight6_29={2,1,5,7};            //图6.33指定权值集合，默认字符集为"ABCD"
        HuffmanTree huftree = new HuffmanTree(charset, weight6_29); //构造Huffman树

        System.out.println(huftree.toString());  //输出Huffman树的结点数组和所有字符编码
        String compressed = huftree.encode(text);
        System.out.println("将"+text+"压缩为"+compressed+"，"+compressed.length()+"位");
        System.out.println("将"+compressed+"解码为"+huftree.decode(compressed));

        int[] weight6_34={5,29,7,8,14,23,3,11};  //图6.34指定权值集合，默认字符集为"ABCDEFGH"
        huftree = new HuffmanTree(weight6_34);   //构造Huffman树
        System.out.println(huftree.toString());  //输出Huffman树的结点数组和所有字符编码
    }
}
