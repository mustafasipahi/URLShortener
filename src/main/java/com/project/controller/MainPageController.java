package com.project.controller;

import com.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainPageController extends AbstractController{

    @Autowired
    private UserService userService;

    @GetMapping(value = "/",produces = {MediaType.TEXT_HTML_VALUE})
    public ModelAndView index(ModelAndView modelAndView){
        getLoginToken();
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
