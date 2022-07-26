package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostDBStore;

import java.util.Collection;
import java.util.List;

@ThreadSafe
@Service
public class PostService {

    private final PostDBStore postStore;
    private final CityService cityService;

    public PostService(PostDBStore postStore, CityService cityService) {
        this.postStore = postStore;
        this.cityService = cityService;
    };

    public Post findById(int id) {
        return postStore.findById(id);
    }

    public Collection<Post> findAll() {
        List<Post> posts = postStore.findAll();
        posts.forEach(post -> post.setCity(cityService.findById(post.getCity().getId())));
        return posts;
    }

    public void add(Post post) {
        postStore.add(post);
    }

    public void update(Post post) {
        postStore.update(post);
    }
}
