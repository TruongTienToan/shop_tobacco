package com.cg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping
    public ModelAndView showListPage() {
        ModelAndView modelAndView = new ModelAndView("/users/list");
        return modelAndView;
    }
}
