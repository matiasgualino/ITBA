package utilities;

public class Test {
    public static void main(String[] args){
        Integer[] list = {5,4,3,2,1,0};
        for(Integer i : list)
        System.out.print(i + ",");
        System.out.println("size: " + list.length);
        System.out.println("");
        ArraySorting.selectionSort2(list);
        for(Integer i : list)
            System.out.print(i + ",");
        System.out.println("size: " + list.length);
    }
}
