package jvm.class_loader;

import java.io.InputStream;

/**
 * @author leofee
 * @date 2021/8/11
 */
public class ClassLoader02 {

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clazz = ClassLoader02.class.getClassLoader().loadClass("jvm.class_loader.Launcher01");
        System.out.println(clazz.getName());

        InputStream resourceAsStream = ClassLoader02.class.getClassLoader().getResourceAsStream("ObjectModel.pbg");
        System.out.println(resourceAsStream);
    }
}
