package jvm.objectmodel;

import java.lang.instrument.Instrumentation;

/**
 * @author leofee
 * @date 2021/8/17
 */
public class ObjectAgent {

    static Instrumentation inst;

    public static void premain(String args, Instrumentation _inst) {
        inst = _inst;
    }

    public static long sizeof(Object o) {
        return inst.getObjectSize(o);
    }
}
