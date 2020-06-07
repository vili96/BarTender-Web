package com.BarTender.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @GetMapping(value = {"/","/index"})
    public ModelAndView home() {
        ModelAndView model = new ModelAndView( );
        model.setViewName( "login" );
        return model;
    }

    @RequestMapping(value = {"/logout"} , method= RequestMethod.GET)
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        ModelAndView model = new ModelAndView( );
        model.setViewName( "redirect:/" );
        return model;
    }
}
