package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository {

    Meal get(int id);

    List<Meal> getAll();

    List<Meal> getBetween(LocalDate startDate, LocalDate endDate);

    Meal save(Meal meal);

    boolean delete(int id);
}
