package jvm.t03_objectmodel;

//import org.openjdk.jol.info.ClassLayout;

/**
 * 利用JOL类库打印对象的大小
 *
 * <pre>{@code
 *
 * <dependency>
 *     <groupId>org.openjdk.jol</groupId>
 *     <artifactId>jol-core</artifactId>
 *     <version>0.10</version>
 * </dependency>
 *
 * }</pre>
 *
 * @author leofee
 */
public class ObjectSizeJol {

    public static void main(String[] args) {
        Object o = new Object();
//        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
