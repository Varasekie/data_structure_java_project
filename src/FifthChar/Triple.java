package FifthChar;

public class Triple implements Comparable<Triple>{

    int row, column,value;
    public Triple(int row,int column ,int value){
        if (row>0 && column >0){
            this.row = row;
            this.column = column;
            this.value = value;
        }
         else throw new IllegalArgumentException("参数异常");

    }

    public Triple(String triple){
        int i= triple.indexOf(","),j = triple.indexOf(",",i+1);
        this.row = Integer.parseInt(triple.substring(1,i));
        this.column = Integer.parseInt(triple.substring(i+1,j));
        this.value = Integer.parseInt(triple.substring(1+j,triple.length()-1));

        if (this.row<0||this.column<0){
            throw new IllegalArgumentException("行/列参数错误");

        }
    }

    public Triple(Triple triple){
        this(triple.row,triple.column,triple.value);
    }
    //转置
    public Triple toSymmetry(){
        return new Triple(this.column,this.row,this.value);
    }

    public boolean equals(Object obj){
        if (obj == this){
            return true;
        }
        if (obj instanceof Triple){
            Triple t = (Triple)obj;
            return this.row == t.row&& this.column == t.column && this.value == t.value;
        }
        return false;
    }

    @Override
    public int compareTo(Triple t) {
        if (this.row == t.row&& this.column == t.column && this.value == t.value){
            return 0;
        }
        return (this.row< t.row || this.row == t.row && this.column< t.column)?-1:1;
    }
}
