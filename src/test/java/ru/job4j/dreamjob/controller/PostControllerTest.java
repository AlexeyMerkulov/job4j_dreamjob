package ru.job4j.dreamjob.controller;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PostControllerTest {

    @Test
    public void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post", "description", LocalDateTime.now()),
                new Post(2, "New post", "description", LocalDateTime.now())
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        HttpSession httpSession = mock(HttpSession.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.posts(model, httpSession);
        verify(model).addAttribute("posts", posts);
        assertEquals(page, "posts");
    }

    @Test
    public void whenFormAddPost() {
        List<City> cities = Arrays.asList(
                new City(1, "Moscow"),
                new City(2, "St.Petersburg")
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(cityService.getAllCities()).thenReturn(cities);
        HttpSession httpSession = mock(HttpSession.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.formAddPost(model, httpSession);
        verify(model).addAttribute("cities", cities);
        assertEquals(page, "addPost");
    }

    @Test
    public void whenFormUpdatePost() {
        Post post = new Post(1, "New post", "description", LocalDateTime.now());
        List<City> cities = Arrays.asList(
                new City(1, "Moscow"),
                new City(2, "St.Petersburg")
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        when(postService.findById(any(Integer.class))).thenReturn(post);
        CityService cityService = mock(CityService.class);
        when(cityService.getAllCities()).thenReturn(cities);
        HttpSession httpSession = mock(HttpSession.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.formUpdatePost(model, httpSession, 1);
        verify(model).addAttribute("post", post);
        verify(model).addAttribute("cities", cities);
        assertEquals(page, "updatePost");
    }

    @Test
    public void whenCreatePost() {
        City moscow = new City(1, "Moscow");
        Post input = new Post(1, "New post", "description", LocalDateTime.now(),
                true, moscow);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(cityService.findById(any(Integer.class))).thenReturn(moscow);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.createPost(input);
        verify(postService).add(input);
        assertEquals(page, "redirect:/posts");
    }

    @Test
    public void whenUpdatePost() {
        City moscow = new City(1, "Moscow");
        Post input = new Post(1, "New post", "description", LocalDateTime.now(),
                true, moscow);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(cityService.findById(any(Integer.class))).thenReturn(moscow);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.updatePost(input);
        verify(postService).update(input);
        assertEquals(page, "redirect:/posts");
    }
}