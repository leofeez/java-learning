package design.responsibilitychain;

/**
 * @author leofee
 */
public class TestChainOfResponsibility {

    public static void main(String[] args) {
        FilterChain chain = new FilterChain();
        chain.addFilter(new UserNameCheckerFilter()).addFilter(new PasswordCheckerFilter());

        Req req = new Req("fuck leofee", "1231231");
        Res res = new Res();

        chain.doFilter(req, res);

    }
}
