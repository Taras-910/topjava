package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static ru.javawebinar.topjava.util.MealsUtil.mealsAdmin;
import static ru.javawebinar.topjava.util.MealsUtil.mealsUser;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());
    public final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        mealsAdmin.forEach(m -> save(m, 1));
        mealsUser.forEach(m -> save(m, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> userMap = repository.getOrDefault(userId, new HashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        userMap.put(meal.getId(), meal);
        repository.put(userId, userMap);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return Optional.ofNullable(repository.get(userId).remove(id)).orElse(null) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return Optional.ofNullable(repository.get(userId).get(id)).orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll userId {}",userId);
        Map<Integer, Meal> mapUser = repository.get(userId);
        return CollectionUtils.isEmpty(mapUser) ? Collections.EMPTY_LIST : mapUser.values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(toList());
    }

    @Override
    public List<Meal> getFilter(LocalDate startDate, LocalDate endDate, int userId) {
        return getAll(userId).stream()
                .filter(m -> m.getDate().isEqual(startDate) || m.getDate().isEqual(endDate)
                        || m.getDate().isAfter(startDate) && m.getDate().isBefore(endDate))
                .collect(Collectors.toList());
    }
}
