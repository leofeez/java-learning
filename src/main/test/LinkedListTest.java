import java.util.*;

/**
 * @author leofee
 */
public class LinkedListTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");

r(list);

    }

    public static void r(List<String> list) {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.println(next);
            if ("d".equals(next)) {
                iterator.remove();
            }
            r(list);
        }
    }

}
