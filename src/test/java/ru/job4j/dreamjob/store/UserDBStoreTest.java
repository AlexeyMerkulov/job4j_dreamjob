package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class UserDBStoreTest {

    private static UserDBStore store = new UserDBStore(new Main().loadPool());

    @Test
    void whenAddNewUsers() {
        User user1 = new User(0, "Andrey", "andrey@yandex.ru", "111");
        User user2 = new User(0, "Elena", "elena@yandex.ru", "111");
        User user3 = new User(0, "Anton", "andrey@yandex.ru", "111");
        store.add(user1);
        store.add(user2);
        store.add(user3);
        List<User> expected = List.of(user1, user2);
        List<User> usersInDB = store.findAll();
        assertThat(expected).isEqualTo(usersInDB);
    }
}