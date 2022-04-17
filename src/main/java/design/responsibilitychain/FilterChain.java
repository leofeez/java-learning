package design.responsibilitychain;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤器链
 *
 * @author leofee
 */
public class FilterChain {

    List<Filter> filters = new ArrayList<>();

    int pos;

    public void doFilter(Req req, Res res) {
        if (pos < filters.size()) {
            filters.get(pos++).doFilter(req, res, this);
            return;
        }

        System.out.println("------do service-------");
    }

    public FilterChain addFilter(Filter filter) {
        this.filters.add(filter);
        return this;
    }
}
