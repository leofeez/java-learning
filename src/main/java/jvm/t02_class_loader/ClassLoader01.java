package jvm.t02_class_loader;

import java.lang.String;
import sun.net.spi.nameservice.dns.DNSNameService;

/**
 * @author leofee
 * @date 2021/8/11
 */
public class ClassLoader01 {

    public static void main(String[] args) {
        // 由 Bootstrap 类加载器加载，返回null，表示顶级类加载器
        System.out.println(String.class.getClassLoader());
        System.out.println(sun.awt.HKSCS.class.getClassLoader());
        // 由 Extension 类加载器加载
        System.out.println(DNSNameService.class.getClassLoader());
        // 由 App 类加载器加载
        System.out.println(ClassLoader01.class.getClassLoader());


        System.out.println("----------------------------------");

        System.out.println(ClassLoader01.class.getClassLoader());
        System.out.println(ClassLoader01.class.getClassLoader().getClass().getClassLoader());
        System.out.println(ClassLoader01.class.getClassLoader().getParent());
        System.out.println(ClassLoader01.class.getClassLoader().getParent().getParent());
    }
}
