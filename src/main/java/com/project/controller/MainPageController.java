package com.project.controller;

import com.project.model.UrlShortener;
import com.project.model.User;
import com.project.services.UrlShortenerService;
import com.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainPageController extends AbstractController{

    @Autowired
    private UserService userService;
    @Autowired
    private UrlShortenerService urlShortenerService;

    @GetMapping(value = "/")
    public String index(Model model){
        boolean isLoggedIn = isLoggedIn();
        User loggedInUser = getLoggedInUser();

        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("loggedInUser", loggedInUser);

        return "index";
    }

    @GetMapping(value = "/account")
    public String account(ModelAndView modelAndView, Model model){
        boolean isLoggedIn = isLoggedIn();
        if (!isLoggedIn) return "redirect:/";
        User loggedInUser = getLoggedInUser();
        List<UrlShortener> urlList = urlShortenerService.findByTokenId(getLoginToken());
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("urlList", urlList);
        return "account";
    }

    @GetMapping(value = "/login")
    public String login(ModelAndView modelAndView, Model model){
        boolean isLoggedIn = isLoggedIn();
        User loggedInUser = getLoggedInUser();
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("loggedInUser", loggedInUser);
        return "login";
    }
}
