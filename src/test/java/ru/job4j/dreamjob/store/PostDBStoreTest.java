package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PostDBStoreTest {
    private static PostDBStore store = new PostDBStore(new Main().loadPool());
    private static CityService cityService  = new CityService();

    @AfterEach
    public void wipeTable() {
        store.deletePosts();
    }

    @Test
    public void whenCreatePost() {
        Post post = new Post(0, "Java Job", "Description",
                LocalDateTime.now(), true, cityService.findById(1));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    public void whenUpdatePost() {
        Post post = new Post(0, "Java Job", "Description",
                LocalDateTime.now(), true, cityService.findById(1));
        store.add(post);
        Post newPost = new Post(post.getId(), "New Java Job", "Description",
                LocalDateTime.now(), true, cityService.findById(1));
        store.update(newPost);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(newPost.getName());
    }

    @Test
    public void whenFindAllPosts() {
        Post post = new Post(0, "Java Job", "Description",
                LocalDateTime.now(), true, cityService.findById(1));
        store.add(post);
        Post newPost = new Post(0, "New Java Job", "New Description",
                LocalDateTime.now(), true, cityService.findById(1));
        store.add(newPost);
        List<Post> postsList = List.of(post, newPost);
        List<Post> postsListFromDB = store.findAll();
        assertThat(postsList).isEqualTo(postsListFromDB);
    }
}