package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.service.UserService;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.USER_MATCHER;

@Profile("datajpa")
public class DatajpaUserServiceTest extends AbstractUserServiceTest {
    @Autowired
    UserService service;

    @Test
    public void getWith() {
        User user = service.getWith(USER_ID);
        USER_MATCHER.assertMatch(user, UserTestData.getWith());
        MEAL_MATCHER.assertMatch(user.getMeals(), UserTestData.getWith().getMeals());
    }

}
