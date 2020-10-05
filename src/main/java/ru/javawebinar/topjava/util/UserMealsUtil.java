package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.mealDate;
import static ru.javawebinar.topjava.util.TimeUtil.mealTime;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        mealsTo = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        Map<LocalDate, Integer> mapCaloriesPerDay = new HashMap<>();
        for(UserMeal meal : meals) {
            mapCaloriesPerDay.merge(mealDate(meal), meal.getCalories(), Integer::sum);
        }
        List<UserMealWithExcess> mealsWithExcess = new ArrayList<>();
        for(UserMeal meal : meals) {
            if(TimeUtil.isBetweenHalfOpen(mealTime(meal), startTime, endTime)) {
                mealsWithExcess.add(mealTo(meal, mapCaloriesPerDay, caloriesPerDay));
            }
        }
        return mealsWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams

        Map<LocalDate, Integer> mapCaloriesPerDay =
                meals.stream().collect(Collectors.groupingBy(TimeUtil::mealDate, Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream().filter(meal -> TimeUtil.isBetweenHalfOpen(mealTime(meal), startTime, endTime))
                .map(meal -> mealTo(meal, mapCaloriesPerDay, caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static UserMealWithExcess mealTo(UserMeal meal, Map<LocalDate, Integer> mapCaloriesPerDay, Integer caloriesPerDay) {
        return new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                mapCaloriesPerDay.get(mealDate(meal)) > caloriesPerDay);
    }
}
