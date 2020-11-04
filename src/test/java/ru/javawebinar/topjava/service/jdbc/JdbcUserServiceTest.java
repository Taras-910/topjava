package ru.javawebinar.topjava.service.jdbc;

import org.springframework.context.annotation.Profile;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

@Profile("jdbc")
public class JdbcUserServiceTest extends AbstractUserServiceTest {
}
