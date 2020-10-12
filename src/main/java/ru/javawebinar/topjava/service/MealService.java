package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    @Autowired
    private MealRepository repository;

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public Meal update(Meal meal, int userId) {
        return checkNotFound(repository.save(meal, userId), "mealId=" + meal.getId());
    }

    public Meal create(Meal meal, int userId) {
        return checkNotFound(repository.save(meal, userId), "");
    }

    public void delete(int id, int userId) {
        checkNotFound(repository.delete(id, userId), " id=" +id);
    }

    public List<Meal> getFilter(LocalDate startDate, LocalDate endDate, int userId) {
        return repository.getFilter(startDate, endDate, userId);
    }
}
