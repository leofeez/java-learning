package design.responsibilitychain;

/**
 * @author leofee
 */
public class PasswordCheckerFilter implements Filter {

    @Override
    public void doFilter(Req req, Res res, FilterChain chain) {
        String pass = req.getPassword();
        System.out.println("pass checker filter start");

        chain.doFilter(req, res);

        res.setPassword("***********");
        System.out.println("pass checker filter end");
    }
}
