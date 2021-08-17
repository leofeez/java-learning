package jvm.class_loader;

import java.io.*;
import java.util.*;

/**
 * 自定义的 class loader
 *
 * @author leofee
 * @date 2021/8/12
 */
public class MyClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String relativePath = name.replaceAll("\\.", "/");
        File file = new File("/Users/leofee/projects/leofee/java-learning/out/production/java-learning/" + relativePath + ".class");

        if (!file.exists()) {
            // throws ClassNotFoundException
            return super.findClass(name);
        }

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             FileInputStream in = new FileInputStream(file)) {

            byte[] bytes = new byte[1024 * 4];

            while (in.read(bytes) != -1) {
                out.write(bytes);
            }
            return defineClass(name, out.toByteArray(), 0, out.toByteArray().length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // throws ClassNotFoundException
        return super.findClass(name);
    }

    public static List<File> doFind(File parent) {
        Objects.requireNonNull(parent);
        List<File> files = new ArrayList<>();
        if (!parent.exists()) {
            return files;
        }

        if (parent.isDirectory()) {
            File[] filesInDirectory = parent.listFiles();
            if (filesInDirectory != null) {
                for (File fileInDirectory : filesInDirectory) {
                    if (fileInDirectory.isDirectory()) {
                        files.addAll(doFind(fileInDirectory));
                    } else {
                        files.add(fileInDirectory);
                    }
                }
            }
        }
        return files;
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyClassLoader classLoader = new MyClassLoader();

        Class<?> hello = classLoader.loadClass("jvm.class_loader.Hello");

        Hello instance = (Hello) hello.newInstance();
        instance.sayHello();
    }
}
