package SecondChar;

public class SortedSinglyList<T extends Comparable<? super T>> extends SinglyList {

    private boolean asc;
    public SortedSinglyList(boolean asc){

    }

    public SortedSinglyList(){
        this(true);
    }

}
