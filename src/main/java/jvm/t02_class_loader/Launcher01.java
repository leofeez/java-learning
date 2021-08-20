package jvm.t02_class_loader;

/**
 *
 *
 * @author leofee
 * @date 2021/4/9
 */
public class Launcher01 {

    public static void main(String[] args){
        String classPath = System.getProperty("java.class.path");
        System.out.println(classPath.replaceAll(":", System.lineSeparator()));
    }
}
