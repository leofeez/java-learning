package jvm.objectmodel;

import java.lang.instrument.Instrumentation;

/**
 * @author leofee
 * @date 2021/8/17
 */
public class ObjectAgent {

    static Instrumentation inst;

    /**
     * 该方法签名是固定的，就和main方法类似
     *
     * @param agentArgs
     * @param _inst
     */

    public static void premain(String agentArgs, Instrumentation _inst) {
        inst = _inst;
    }

    public static long sizeof(Object o) {
        return inst.getObjectSize(o);
    }
}
