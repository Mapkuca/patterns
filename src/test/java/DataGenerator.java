import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {

    private DataGenerator() {
    }

    private static Faker getFaker() {
        return new Faker(new Locale("ru"));
    }

    public static LocalDate generateDate() {
        long minDay = LocalDate.now().plusDays(1).toEpochDay();
        long maxDay = LocalDate.now().plusMonths(2).toEpochDay();
        long someday = ThreadLocalRandom.current().nextLong(maxDay, maxDay);
        return LocalDate.ofEpochDay(someday);
    }

    public static String generateCity() {
        return DataGenerator.getFaker().address().city();
    }

    public static String generateName() {
        return DataGenerator.getFaker().name().fullName();
    }

    public static String generatePhone() {
        return DataGenerator.getFaker().phoneNumber().phoneNumber();
    }
}
