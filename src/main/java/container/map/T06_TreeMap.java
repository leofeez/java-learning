package container.map;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * @author leofee
 */
public class T06_TreeMap {

    public static void main(String[] args) {
        // 按照自然排序
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("aa", "bbb");

        // 支持自定义的Comparator比较器
        TreeMap<String, Object> treeMap2 = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        });
    }

    static class Person implements Comparable<Person>{
        private int age;


        @Override
        public int compareTo(Person o) {
            return this.age - o.age;
        }
    }
}
