package design.factory.product;

public class IntelCpu implements CPU {

    @Override
    public void run() {
        System.out.println("Intel CPU running");
    }
}
