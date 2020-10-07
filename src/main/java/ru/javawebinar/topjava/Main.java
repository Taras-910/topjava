package ru.javawebinar.topjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.util.MealsUtil.CALORIES_REP_DAY;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

/**
 * @see <a href="http://topjava.herokuapp.com">Demo application</a>
 * @see <a href="https://github.com/JavaOPs/topjava">Initial project</a>
 */
public class Main {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
//        MealMemory memory = new MealMemory();
        MealService service = new MealService();
        System.out.println(filteredByStreams(service.getAll(), LocalDateTime.MIN.toLocalTime(), LocalDateTime.MAX.toLocalTime(), CALORIES_REP_DAY));

    }
}
