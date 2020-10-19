package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL1_ID = START_SEQ + 2;

    public static final Meal MEAL1 = new Meal(MEAL1_ID, LocalDateTime.of(2020, 1, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(MEAL1_ID +1, LocalDateTime.of(2020, 1, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL3 = new Meal(MEAL1_ID +2, LocalDateTime.of(2020, 1, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL4 = new Meal(MEAL1_ID +3, LocalDateTime.of(2020, 1, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal MEAL5 = new Meal(MEAL1_ID +4, LocalDateTime.of(2020, 1, 31, 10, 0), "Завтрак", 410);
    public static final Meal MEAL6 = new Meal(MEAL1_ID +5, LocalDateTime.of(2020, 1, 31, 13, 0), "Обед", 510);
    public static final Meal MEAL7 = new Meal(MEAL1_ID +6, LocalDateTime.of(2020, 1, 31, 20, 0), "Ужин", 1500);
    public static final Meal MEAL8 = new Meal(MEAL1_ID +7, LocalDateTime.of(2015, 6, 1, 14, 0), "Админ ланч", 510);
    public static final Meal MEAL9 = new Meal(MEAL1_ID +8, LocalDateTime.of(2015, 6, 1, 21, 0), "Админ ужин", 1500);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "new Eda", 1000);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL1_ID, MEAL1.getDateTime(), "UpdatedName", 330);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("").isEqualTo(expected);
    }

}
