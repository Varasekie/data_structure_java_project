package FifthChar;

public class Yanghui2 {
    public static int[][] create(final int n)    //计算n行杨辉三角，返回n行下三角形的二维数组
    {
        int[][] mat = new int[n][];              //申请第一维的存储空间。局部变量，动态数组
        for(int i=0; i<n; i++)
        {
            mat[i]= new int[i+1];                //申请第二维的存储空间，每次长度不同
            mat[i][0]=mat[i][i]=1;               //每行两端数值为1
            for(int j=1; j<i; j++)
                mat[i][j]=mat[i-1][j-1]+mat[i-1][j];  //中间元素等于它上一行前两个数值之和
        }
        return mat;                              //返回二维数组引用，未释放数组空间
    }

    public static void print(int[][] mat)        //输出二维数组，杨辉三角每行带有前导空格
    {
        for(int i=0; i<mat.length; i++)
        {
            System.out.print(String.format("%"+(mat.length-i+1)*2+"c",' '));//输出前导空格
            for(int j=0; j<mat[i].length; j++)
//              System.out.print(" "+mat[i][j]);
                System.out.print(String.format("%4d",mat[i][j]));//以4位宽度输出十进制整数
            System.out.println();
        }
    }

    public static void main(String[] args)
    {
        Yanghui2.print(Yanghui2.create(6));
    }
}
