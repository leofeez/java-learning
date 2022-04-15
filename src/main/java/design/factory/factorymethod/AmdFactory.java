package design.factory.factorymethod;

import design.factory.product.AmdCpu;
import design.factory.product.CPU;

class AmdFactory implements ICpuFactory {
    @Override
    public CPU create() {
        return new AmdCpu();
    }
}
