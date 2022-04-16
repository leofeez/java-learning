package design.factory.spring.product;

import design.factory.spring.Strategy;

/**
 * @author leofee
 */
public interface Cpu extends Strategy<Cpu.Brand> {

    @Override
    default Brand identity() {
        return brand();
    }

    Brand brand();

    void run();

    enum Brand {
        AMD,
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
