package jvm.class_loader;

import java.io.File;

/**
 * 自定义的 class loader
 *
 * @author leofee
 * @date 2021/8/12
 */
public class MyClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File("/Users/leofee/projects/leofee/java-learning/out/production/java-learning/jvm/class_loader/Hello.class");



        return super.findClass(name);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        MyClassLoader classLoader = new MyClassLoader();

        classLoader.loadClass("jvm.class_loader.Hello");

        System.out.println(classLoader.getParent());
    }
}
