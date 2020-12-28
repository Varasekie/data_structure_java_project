package FifthChar;

public class ProcessDispatch {
    public static void main(String args[])
    {
        Process[] process={new Process("A",4), new Process("B",8), new Process("C",5),
                new Process("D",9), new Process("E",7), new Process("F",3)};
        PriorityQueue<Process> que = new PriorityQueue<Process>(process, false); //优先队列按优先级降序排队

/*        //import java.util.PriorityQueue;
        PriorityQueue<Process> que = new PriorityQueue<Process>();//优先队列，默认升序
        System.out.print("入队元素：");
        for(int i=0;  i<process.length;  i++)
        {
        	System.out.print(process[i]+" ");
            que.add(process[i]);                   //元素入队
        }
        System.out.println("\n"+que.toString());
*/
        System.out.print("出队元素：");
        while(!que.isEmpty())
            System.out.print(que.poll().toString()+" ");   //元素出队
        System.out.println();
    }
}
