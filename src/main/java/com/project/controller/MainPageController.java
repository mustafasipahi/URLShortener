package com.project.controller;

import com.project.model.User;
import com.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainPageController extends AbstractController{

    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public ModelAndView index(ModelAndView modelAndView, Model model){
        boolean isLoggedIn = isLoggedIn();
        User loggedInUser = getLoggedInUser();

        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("loggedInUser", loggedInUser);

        modelAndView.setViewName("index");
        return modelAndView;
    }
}
