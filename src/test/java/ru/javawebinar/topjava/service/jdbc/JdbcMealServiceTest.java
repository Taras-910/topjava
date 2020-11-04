package ru.javawebinar.topjava.service.jdbc;

import org.springframework.context.annotation.Profile;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

@Profile("jdbc")
public class JdbcMealServiceTest extends AbstractMealServiceTest {
}
