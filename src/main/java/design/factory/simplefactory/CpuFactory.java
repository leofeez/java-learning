package design.factory.simplefactory;

import design.factory.product.AmdCpu;
import design.factory.product.CPU;
import design.factory.product.IntelCpu;

/**
 * 简单工厂模式：
 * 只适合于产品对象较少，且产品固定的需求，对于产品变化无常的需求来说显然不合适。
 * <p>
 * 缺点：
 * <p>
 * 如果增加新产品类，工厂的创建方法中就要增加新的if-else，
 * 这种做法扩展性差，违背了开闭原则，也影响了可读性。
 * 所以，这种方式使用在业务较简单，工厂类不会经常更改的情况。
 *
 * @author leofee
 */
class CpuFactory {

    public static CPU create(String type) {

        if ("Intel".equals(type)) {
            return new IntelCpu();
        } else if ("Amd".equals(type)) {
            return new AmdCpu();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
