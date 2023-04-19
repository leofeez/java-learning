package design.builder;

/**
 * @author leofee
 */
public class LenovoComputerBuilder extends ComputerBuilder {

    @Override
    protected void addCpu(String cpu) {
        computer.setCpu("Amd cpu");
    }

    @Override
    protected void addMainAboard(String mainAboard) {
        computer.setMainabord("Lenovo mainaboard");
    }

    @Override
    protected void addMemory(String memory) {
        computer.setMemory("16G");
    }

    @Override
    protected void addKeyboard(String keyboard) {
        computer.setKeyboard("Lenovo keyboard");
    }

    @Override
    protected void addScreen(String screen) {
        computer.setKeyboard("Lenovo screen");
    }

    @Override
    protected Computer getComputer() {
        computer.setBrand("Lenovo");
        return computer;
    }
}
