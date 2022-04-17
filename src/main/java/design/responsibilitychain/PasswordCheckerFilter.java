package design.responsibilitychain;

/**
 * @author leofee
 */
public class PasswordCheckerFilter implements Filter {

    @Override
    public void doFilter(Req req, Res res, FilterChain chain) {
        String pass = req.getPassword();
        System.out.println("pass checker filter start");

        res.setPassword("***********");
        chain.doFilter(req, res);

        System.out.println("pass checker filter end");
    }
}
