package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostStore {
    private static final PostStore INST = new PostStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(3);

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "Требумый опыт работы от 1 года",
                LocalDateTime.of(2022, 6, 30, 13, 54)));
        posts.put(2, new Post(2, "Middle Java Job", "Требуемый опыт работы от 2 лет",
                LocalDateTime.of(2022, 7, 6, 19, 30)));
        posts.put(3, new Post(3, "Senior Java Job", "Требуемый опыт работы от 5 лет",
                LocalDateTime.of(2022, 7, 11, 15, 1)));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        post.setId(counter.incrementAndGet());
        posts.put(post.getId(), post);
    }

    public void update(Post post) {
        posts.replace(post.getId(), post);
    }
}
