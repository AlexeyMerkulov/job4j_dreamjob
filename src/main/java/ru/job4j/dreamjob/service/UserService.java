package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.store.UserDBStore;

import java.util.Optional;

@ThreadSafe
@Service
public class UserService {

    private final UserDBStore userStore;

    public UserService(UserDBStore userStore) {
        this.userStore = userStore;
    };

    public Optional<User> add(User user) {
        Optional<User> rsl =  Optional.ofNullable(userStore.add(user));
        return rsl;
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        Optional<User> rsl =  Optional.ofNullable(userStore.findUserByEmailAndPwd(email, password));
        return rsl;
    }
}