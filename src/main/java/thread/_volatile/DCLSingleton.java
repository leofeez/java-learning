package thread._volatile;

/**
 * @author leofee
 * @date 2020/12/3
 */
public class DCLSingleton {

    /**
     * volatile的作用就是防止指令重排序导致在高并发的情况下读到成员变量在赋值过程中的中间值
     */
    private static volatile DCLSingleton INSTANCE;

    private DCLSingleton() {
    }

    public static DCLSingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (DCLSingleton.class) {
                // 双重检查，防止在第一个判断会有多个线程都判断为NULL
                // 自然就会进入synchronized代码块进行实例化
                if (INSTANCE == null) {

                    // JVM 虚拟机编译之后为多个指令
                    // 1. 申请内存
                    // 2. 成员变量初始化(本例中的volatile的作用就是防止指令重排序导致在高并发的情况下读到成员变量在赋值过程中的中间值)
                    // 3. 内存赋值给INSTANCE
                    INSTANCE = new DCLSingleton();
                }
            }
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> System.out.println(getInstance())).start();
        }
    }
}
