package design.factory;

/**
 * 简单工厂模式
 *
 * 简单工厂只适合于产品对象较少，且产品固定的需求，对于产品变化无常的需求来说显然不合适
 *
 * @author leofee
 */
public class SimpleFactory {

    static class CpuFactory {

        public static final CPU getCpu(String type) {
            if ("Intel".equals(type)) {
                return new IntelCpu();
            } else if ("Amd".equals(type)) {
                return new AmdCpu();
            } else {
                return null;
            }
        }
    }

    interface CPU {
        void create();
    }

    static class IntelCpu implements CPU {

        @Override
        public void create() {
            System.out.println("Intel CPU");
        }
    }

    static class AmdCpu implements CPU {

        @Override
        public void create() {
            System.out.println("Amd CPU");
        }
    }

    public static void main(String[] args) {
        CpuFactory.getCpu("Amd").create();;
    }
}
