package design.responsibilitychain;

/**
 * @author leofee
 */
public interface Filter {

    void doFilter(Req req, Res res, FilterChain chain);
}
