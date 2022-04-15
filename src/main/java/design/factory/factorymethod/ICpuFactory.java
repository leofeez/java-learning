package design.factory.factorymethod;

import design.factory.product.CPU;

/**
 * 工厂方法模式：
 * 工厂子类实现同一个抽象工厂接口。这样，创建不同产品时，只需要实现不同的工厂子类。
 * 当有新产品加入时，新建具体工厂继承抽象工厂，而不用修改任何一个类。
 * <p>
 * 缺点：
 * 每一种产品对应一个工厂子类，在创建具体产品对象时，实例化不同的工厂子类。
 * 但是，如果业务涉及的子类越来越多，难道每一个子类都要对应一个工厂类吗？
 * 这样会使得系统中类的个数成倍增加，增加了代码的复杂度。
 *
 * @author leofee
 */
interface ICpuFactory {

    CPU create();
}
