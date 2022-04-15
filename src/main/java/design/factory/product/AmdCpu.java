package design.factory.product;

public class AmdCpu implements CPU {

    @Override
    public void run() {
        System.out.println("Amd CPU running");
    }
}
