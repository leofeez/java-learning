package design.strategy;

/**
 * 策略模式接口
 *
 * @author leofee
 */
public interface Strategy<I extends Enum<?>> {

    /**
     * 策略的唯一标识
     */
    I identity();
}
