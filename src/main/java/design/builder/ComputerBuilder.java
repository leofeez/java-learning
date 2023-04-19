package design.builder;

import design.strategy.Cpu;

/**
 * @author leofee
 */
public abstract class ComputerBuilder {

    protected Computer computer = new Computer();

    protected abstract void addCpu(String cpu);
    protected abstract void addMainAboard(String mainAboard);
    protected abstract void addMemory(String memory);
    protected abstract void addKeyboard(String keyboard);
    protected abstract void addScreen(String screen);
    protected abstract Computer getComputer();
}
