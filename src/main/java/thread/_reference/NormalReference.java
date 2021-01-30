package thread._reference;

import thread._threadlocal.Person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leofee
 * @date 2020/12/8
 */
public class NormalReference {

    public static void main(String[] args) throws IOException {
        Person person = new Person("张三");

        // 当引用传递设置为null时无法影响传递内的结果
        List<Person> personList = new ArrayList<>();
        personList.add(person);

        Map<Person, Person> map = new HashMap<>();
        map.put(person, person);

        person = null;

        System.out.println(map.keySet().iterator().next());
        System.out.println(personList.get(0));

        System.gc();

        System.in.read();

    }
}
