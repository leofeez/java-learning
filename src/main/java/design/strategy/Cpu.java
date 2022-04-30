package design.strategy;

/**
 * @author leofee
 */
public interface Cpu extends Strategy<Cpu.Brand> {

    @Override
    default Brand identity() {
        return brand();
    }

    /**
     * 将品牌作为策略的唯一标识
     */
    Brand brand();

    /**
     * CPU的行为
     */
    void run();

    /**
     * 品牌枚举
     */
    enum Brand {
        /** AMD*/
        AMD,
        /** Intel*/
        INTEL
    }


    class AmdCpu implements Cpu {

        @Override
        public Brand brand() {
            return Brand.AMD;
        }

        @Override
        public void run() {
            System.out.println("AMD yyds");
        }
    }

    class IntelCpu implements Cpu {

        @Override
        public Brand brand() {
            return Brand.INTEL;
        }

        @Override
        public void run() {
            System.out.println("Intel not good");
        }
    }
}
