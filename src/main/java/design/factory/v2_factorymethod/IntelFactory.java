package design.factory.v2_factorymethod;

import design.factory.product.CPU;
import design.factory.product.IntelCpu;

class IntelFactory implements ICpuFactory {
    @Override
    public CPU create() {
        return new IntelCpu();
    }
}
