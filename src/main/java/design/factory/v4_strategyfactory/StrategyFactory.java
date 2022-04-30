package design.factory.v4_strategyfactory;


import design.factory.v4_strategyfactory.spring.ApplicationContext;
import design.factory.v4_strategyfactory.spring.ApplicationContextAware;
import design.factory.v4_strategyfactory.spring.InitializingBean;
import design.strategy.Cpu;
import design.strategy.Strategy;

import java.util.*;

/**
 * 策略工厂
 *
 * 将所有继承了{@link Strategy} 接口的策略实现存放在该 Factory 工厂类中
 *
 * @author leofee
 */
//@Component
public class StrategyFactory implements ApplicationContextAware, InitializingBean {

    private static ApplicationContext applicationContext;

    /**
     * 策略类型清单
     * 若需要添加新的策略接口，则添加对应的接口类型即可（前提：策略接口必须继承 {@link Strategy}）
     * <pre>{@code
     *      strategyTypes.add(NewStrategy2.class);
     *      strategyTypes.add(NewStrategy3.class);
     *      strategyTypes.add(NewStrategy4.class);
     * }</pre>
     */
    static Set<Class<? extends Strategy<?>>> strategyTypes = new HashSet<>();

    static {
        strategyTypes.add(Cpu.class);
    }

    /**
     * 策略实现清单
     */
    static Map<Enum<?>, Strategy<?>> strategyMap = new HashMap<>();

    /**
     * Spring 容器初始化后装载对应的策略实现到 {@code strategyMap} 中
     */
    @Override
    public void afterPropertiesSet() {
        for (Class<? extends Strategy<?>> strategyType : strategyTypes) {
            Collection<? extends Strategy<?>> strategies =
                    applicationContext.getBeansOfType(strategyType).values();
            strategies.forEach(strategy -> strategyMap.put(strategy.identity(), strategy));
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        StrategyFactory.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <I extends Enum<?>, T extends Strategy<I>> T getStrategy(I identity, Class<T> strategyType) {
        Strategy<?> strategy = strategyMap.get(identity);
        if (strategy.getClass().isAssignableFrom(strategyType)) {
            throw new ClassCastException("strategy name " + identity.name() + " found strategy instance is " + strategy + ", but class is not same as " + strategyType);
        }
        return (T) strategy ;
    }

    public static <I extends Enum<?>> StrategyAdder addStrategy(I identity, Strategy<?> strategy) {
        return new StrategyAdder().addStrategy(identity, strategy);
    }

    static class StrategyAdder {
        public StrategyAdder addStrategy(Enum<?> identity, Strategy<?> strategy) {
            strategyMap.put(identity, strategy);
            return this;
        }
    }
}
