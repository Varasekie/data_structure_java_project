package FifthChar;

public class Array2 {
    //输出二维数组，每元素占4个字符，不足时前补空格；每行结束输出换行符
    //二维数组作为参数，通用算法
    public static void print(int[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++)
                System.out.print(String.format("%4d", mat[i][j]));//格式化输出
            System.out.println();
        }
    }


    public static int[][] create(int n)     //上三角二维数组
    {
        int[][] mat = new int[n][];
        int k = 1;
        for (int i = 0; i < n; i++) {
            mat[i] = new int[n - i];
            for (int j = 0; j < n - i; j++)
                mat[i][j] = k++;
        }
        return mat;
    }

    public static void main(String args[]) {
//        int mat[][] = { {1,2,3},{4,5,6} };
//        print(mat);

        print(create(8));
    }
}
