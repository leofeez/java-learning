package design.factory.v3_abstractfactory;

import design.factory.product.Keyboard;
import design.factory.product.Mouse;

/**
 * 为了缩减工厂实现子类的数量，不必给每一个产品分配一个工厂类，
 * 可以将产品进行分组，每组中的不同产品由同一个工厂类的不同方法来创建。
 *
 * <p>
 * 抽象工厂适用于以下情况：
 *
 * 1. 一个系统要独立于它的产品的创建、组合和表示时；
 * 2. 一个系统要由多个产品系列中的一个来配置时；
 * 3. 要强调一系列相关的产品对象的设计以便进行联合使用时；
 * 4. 当你提供一个产品类库，而只想显示它们的接口而不是实现时；
 *
 * @author leofee
 */
public interface IFactory {

    Keyboard createKeyboard();

    Mouse createMouse();

}
