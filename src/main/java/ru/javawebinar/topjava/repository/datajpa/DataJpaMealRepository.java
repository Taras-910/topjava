package ru.javawebinar.topjava.repository.datajpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Profile("datajpa")
@Repository
public class DataJpaMealRepository implements MealRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final CrudMealRepository crudRepository;
    private final CrudUserRepository crudUserRepository;

    public DataJpaMealRepository(CrudMealRepository crudRepository, CrudUserRepository crudUserRepository) {
        this.crudRepository = crudRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        User user = crudUserRepository.findById(userId).orElse(null);
        meal.setUser(user);
        if (meal.isNew()) {
            return Optional.ofNullable(crudRepository.save(meal)).orElse(null);
        }
        return user != null && get(meal.getId(), userId) != null ? Optional.ofNullable(crudRepository.save(meal)).orElse(null) : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get id {} for user {}", id, userId);
        Meal meal = crudRepository.findById(id).orElse(null);
        return meal != null && meal.getUser().getId() != userId ? null : meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return Optional.ofNullable(crudRepository.getAll(userId)).orElse(null);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return Optional.ofNullable(crudRepository.getBetweenHalfOpen(startDateTime, endDateTime, userId)).orElse(null);
    }
}
