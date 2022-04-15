package design.factory.simplefactory;

class TestSimpleFactory {

    public static void main(String[] args) {
        CpuFactory.create("Amd").run();
    }
}
