package SoftwareDevelopDomain;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class Helpers {
    private static final AtomicLong TIME_STAMP = new AtomicLong();
    // can have up to 1000 ids per second.
    public static long getUniqueMillis() {
        long now = System.currentTimeMillis();
        while (true) {
            long last = TIME_STAMP.get();
            if (now <= last)
                now = last + 1;
            if (TIME_STAMP.compareAndSet(last, now))
                return now;
        }
    }

    public static final long getMillisecFromDate(LocalDateTime ldt)
    {
        //LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }

}
