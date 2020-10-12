package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.model.Role.ADMIN;
import static ru.javawebinar.topjava.model.Role.USER;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public static final List<User> users = Arrays.asList(
            new User(null, "", "user@yandex.ru", "", 1500, true, Collections.singleton(USER)),
            new User(null, "", "admin@gmail.com", "", 2000, true, Collections.singleton(ADMIN))
    );

    {
        users.forEach(this::save);
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if(user.isNew()){
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
        }
        else{
            repository.computeIfPresent(user.getId(), (k, v) -> user);
        }
        return user;
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return Optional.ofNullable(repository.get(id)).orElse(null);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return repository.values().stream()
                .sorted((o1, o2) -> o1.getName() != o2.getName()? o1.getName().
                        compareTo(o2.getEmail()): o1.getEmail().compareTo(o2.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.values().stream()
                .filter((u) -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}
