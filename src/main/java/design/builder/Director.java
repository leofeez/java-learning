package design.builder;

/**
 * @author leofee
 */
public class Director {

    ComputerBuilder builder;

    public Director(ComputerBuilder builder) {
        this.builder = builder;
    }

    public Computer build() {
        builder.addCpu("");
        builder.addMainAboard("");
        builder.addMemory("");
        builder.addScreen("");
        builder.addKeyboard("");

        Computer computer = builder.getComputer();
        return computer;
    }

    public static void main(String[] args) {
        LenovoComputerBuilder builder = new LenovoComputerBuilder();
        Director director = new Director(builder);
        Computer computer = director.build();
        System.out.println(computer);
    }
}
