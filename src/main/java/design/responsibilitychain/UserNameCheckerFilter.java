package design.responsibilitychain;

/**
 * @author leofee
 */
public class UserNameCheckerFilter implements Filter {

    @Override
    public void doFilter(Req req, Res res, FilterChain chain) {
        String username = req.getUsername();
        System.out.println("username checker filter start");

        req.setUsername(username.replaceAll("fuck", ""));
        chain.doFilter(req, res);

        System.out.println("username checker filter end");

    }
}
