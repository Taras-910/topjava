package ru.javawebinar.topjava.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MealMemory implements MealRepository{
    public static final Logger log = LoggerFactory.getLogger(MealMemory.class);

    public MealMemory() {
        initMeals();
    }

    public static AtomicInteger count = new AtomicInteger(0);
    public static Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    public static List<Meal> list = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    private void initMeals() {
        list.forEach(meal -> {
            Integer id = count.incrementAndGet();
            meal.setId(id);
            meals.put(id, meal);
        });
    }

    @Override
    public Meal get(int id) {
        return Optional.ofNullable(meals.get(id)).orElse(null);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<Meal>(meals.values());
    }

    @Override
    public List<Meal> getBetween(LocalDate startDate, LocalDate endDate) {
        return meals.values().stream()
                .filter(m -> startDate.isEqual(m.getDate()) && startDate.isBefore(m.getDate()) && m.getDate().isBefore(endDate))
                .collect(Collectors.toList());
    }

    @Override
    public Meal save(Meal meal) {
        log.info("save id {}", meal.getId());
        if(meal.getId() == null){
            meal.setId(count.incrementAndGet());
        }
        meals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id) {
        return Optional.ofNullable(meals.remove(id)).orElse(null) != null;
    }
}
