package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealMemory;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.util.List;

public class MealService {
    public static Logger log = LoggerFactory.getLogger(MealService.class);
    private MealRepository repository;

    public MealService() {
        repository = new MealMemory();
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    public List<Meal> getAll() {
        log.info("getAll");
        return repository.getAll();
    }

    public List<Meal> getBetween(LocalDate startDate, LocalDate endDate) {
        log.info("getBetween {} - {}", startDate, endDate);
        return repository.getBetween(startDate, endDate);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        return repository.save(meal);
    }

    public Meal update(Meal meal, int id) {
        log.info("update {}", meal);
        if(meal.id != id) {
            return repository.save(meal);
        }
        return null;
    }

    public void delete(int id) {
        log.info("delete {}", id);
        repository.delete(id);
    }
}
