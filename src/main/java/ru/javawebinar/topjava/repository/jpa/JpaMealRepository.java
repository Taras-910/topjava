package ru.javawebinar.topjava.repository.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            Meal fromDB = get(meal.getId(), userId);
            return fromDB != null && fromDB.getUser().getId() == userId ? em.merge(meal) : null;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Query query = em.createQuery("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId");
        return Optional.of(query.setParameter("id", id).setParameter("userId", userId).executeUpdate()).orElse(0) != 0;

    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = Optional.ofNullable(em.find(Meal.class, id)).orElse(null);
        return meal == null || meal.getUser().getId() != userId ? null : meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createNamedQuery(Meal.BETWEEN, Meal.class)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .setParameter("userId", userId)
                .getResultList();
    }
}
