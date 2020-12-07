package date;

import java.text.SimpleDateFormat;

/**
 * @author leofee
 */
public class DateUtilsThreadSafeTest {

    public static void main(String[] args) {

        new Thread(DateUtilsThreadSafeTest::getFormatter, "A").start();

        new Thread(DateUtilsThreadSafeTest::getFormatter, "B").start();
    }

    private static void getFormatter() {
        SimpleDateFormat formatter = DateUtils.getFormatter("yyyy-MM-dd");
        System.out.println(Thread.currentThread().getName() + formatter);
    }
}
