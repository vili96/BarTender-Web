package com.BarTender.controllers;

import com.BarTender.services.UserLoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
//    @RequestMapping(value = {"/users/managers"} , method= RequestMethod.GET)
//    public ModelAndView getAllManagers(HttpSession session) {
//        ModelAndView model = new ModelAndView();
//        if (session.getAttribute("userId") == null || session.getAttribute("userId").toString().equals("")) {
//            model.setViewName( "login" );
//        }
//        UserLoginService userLoginService = new UserLoginService();
//        model.addObject("managers", userLoginService.getAllManagers());
//        return model;
//    }
}
