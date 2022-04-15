package design.factory.factorymethod;

class TestFactoryMethod {

    public static void main(String[] args) {
        ICpuFactory amdFactory = new AmdFactory();
        amdFactory.create().run();

        ICpuFactory intelFactory = new IntelFactory();
        intelFactory.create().run();
    }
}
