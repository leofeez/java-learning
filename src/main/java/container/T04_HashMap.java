package container;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leofee
 */
public class T04_HashMap {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Map<String, Object> map = new HashMap<>();


        final Method method = HashMap.class.getDeclaredMethod("capacity");
        method.setAccessible(true);

        final Field field = HashMap.class.getDeclaredField("threshold");
        field.setAccessible(true);

        System.out.println("threshold : " + field.get(map));
        System.out.println("capacity  : " + method.invoke(map));


        map.put("1", "2");

        System.out.println("threshold : " + field.get(map));
        System.out.println("capacity  : " + method.invoke(map));


    }
}
