package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@ThreadSafe
@Controller
public class PostController {

    private final PostService postService;
    private final CityService cityService;

    public PostController(PostService postService, CityService cityService) {
        this.postService = postService;
        this.cityService = cityService;
    }

    @GetMapping("/posts")
    public String posts(Model model, HttpSession session) {
        UserController.userCheck(model, session);
        model.addAttribute("posts", postService.findAll());
        return "posts";
    }

    @GetMapping("/formAddPost")
    public String formAddPost(Model model, HttpSession session) {
        model.addAttribute("cities", cityService.getAllCities());
        UserController.userCheck(model, session);
        return "addPost";
    }

    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model, HttpSession session, @PathVariable("postId") int id) {
        UserController.userCheck(model, session);
        Post post = postService.findById(id);
        post.setVisible(false);
        model.addAttribute("post", post);
        model.addAttribute("cities", cityService.getAllCities());
        return "updatePost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post) {
        LocalDateTime now = LocalDateTime.now();
        post.setCreated(LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(),
                now.getHour(), now.getMinute()));
        City city = cityService.findById(post.getCity().getId());
        post.setCity(city);
        postService.add(post);
        return "redirect:/posts";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post) {
        LocalDateTime now = LocalDateTime.now();
        post.setCreated(LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(),
                now.getHour(), now.getMinute()));
        City city = cityService.findById(post.getCity().getId());
        post.setCity(city);
        postService.update(post);
        return "redirect:/posts";
    }
}
