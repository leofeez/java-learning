package jvm.t04_dataarea;

/**
 * @author leofee
 * @date 2021/8/19
 */
public class T03_Stack {

    public static void main(String[] args) {
        int i = 200;
        int b = 100;
        Calculator c = new Calculator();
//        c.p1();
//        c.p2(i);
//        c.p3(i);
        c.add(i, b);

//        List<Integer> list = new ArrayList<>();
//        list.add(1); // 这里就会是invokeInterface

//        ArrayList<Integer> list2 = new ArrayList<>();
//        list2.add(1); // 这里就会是invokeInterface


    }
}
