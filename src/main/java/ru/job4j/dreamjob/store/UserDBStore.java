package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ThreadSafe
@Repository
public class UserDBStore {

    private static final Logger LOG = LoggerFactory.getLogger(UserDBStore.class.getName());

    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM users")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(getUser(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception caught", e);
        }
        return users;
    }

    public User add(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO users(name, email, password) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            setPreparedStatement(ps, user);
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (PSQLException e) {
            if (e.getMessage().contains(
                    "ОШИБКА: повторяющееся значение ключа нарушает ограничение уникальности \"email_unique\"")) {
                return null;
            } else {
                LOG.error("PSQLException caught", e);
            }
        } catch (Exception e) {
            LOG.error("Exception caught", e);
        }
        return user;
    }

    public User findUserByEmailAndPwd(String email, String password) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?")) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return getUser(it);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception caught", e);
        }
        return null;
    }

    public void deleteUsers() {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("DELETE FROM users")) {
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception caught", e);
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password")
        );
    }

    private void setPreparedStatement(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getEmail());
        preparedStatement.setString(3, user.getPassword());
    }
}