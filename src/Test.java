import Last.SeqList;

public class Test {

    public static void main(String[] args) {
        Integer[] ints = new Integer[10];
        Integer[] ints1 = new Integer[10];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = i;
            ints1[i] = 10-i ;
        }
        SeqList list1 = new SeqList(ints);
        SeqList list2 = new SeqList(ints1);
        boolean find = true;
        for (int i = 0; i < list1.size(); i++) {
            //只有每个都找得到才能返回true
            int j = 0;
            for (j = 0; j < list2.size(); j++) {
                if (list1.get(i).equals(list2.get(j))) {
                    break;
                }
            }
            //走到这里说明在前面都没找到
//            return false;
            if (j == list2.size()) {
                find = false;
            }
        }

        System.out.println(find);
    }
}
