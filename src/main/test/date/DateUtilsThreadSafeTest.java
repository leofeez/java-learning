package date;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author leofee
 */
public class DateUtilsThreadSafeTest {

    public static void main(String[] args) {
        for (int i = 0; i < 1000000; i++) {
            new Thread(() -> {
                SimpleDateFormat formatInstance = DateUtils.getFormatInstance2("yyyy-MM-dd HH:mm:ss.SSS");
                System.out.println(formatInstance);
            }).start();
        }
    }
}
