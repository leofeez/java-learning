package design.factory.spring;

import design.factory.spring.product.Cpu;
import design.factory.spring.product.Keyboard;

/**
 * @author leofee
 */
public class TestStrategyFactory {

    public static void main(String[] args) {
        // 添加两种产品
        StrategyFactory.addStrategy(Cpu.Brand.AMD, new Cpu.AmdCpu())
                .addStrategy(Cpu.Brand.INTEL, new Cpu.IntelCpu())
                // 添加另外一类产品
                .addStrategy(Keyboard.KeyBordBrand.DELL, new Keyboard.DellKeyBoard());


        Cpu cpu = StrategyFactory.getStrategy(Cpu.Brand.AMD, Cpu.class);
        cpu.run();

        Keyboard keyboard = StrategyFactory.getStrategy(Keyboard.KeyBordBrand.DELL, Keyboard.class);
        keyboard.input("hello world");
    }
}
