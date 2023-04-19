package design.builder;

/**
 * @author leofee
 */
public class AsusComputerBuilder extends ComputerBuilder {

    @Override
    protected void addCpu(String cpu) {
        computer.setCpu("Intel cpu");
    }

    @Override
    protected void addMainAboard(String mainAboard) {
        computer.setMainabord("Asus mainaboard");
    }

    @Override
    protected void addMemory(String memory) {
        computer.setMemory("32G");
    }

    @Override
    protected void addKeyboard(String keyboard) {
        computer.setKeyboard("Logitech keyboard");
    }

    @Override
    protected void addScreen(String screen) {
        computer.setKeyboard("Aus screen");
    }

    @Override
    protected Computer getComputer() {
        computer.setBrand("Asus");
        return computer;
    }

}
