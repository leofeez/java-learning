package design.factory.v1_simplefactory;

class TestSimpleFactory {

    public static void main(String[] args) {
        CpuFactory.create("Amd").run();
    }
}
