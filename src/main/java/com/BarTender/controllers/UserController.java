package com.BarTender.controllers;

import com.BarTender.models.User;
import com.BarTender.services.UserLoginService;
import com.BarTender.utils.AppConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private int adminRole = AppConstants.RoleConstants.ADMIN;
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
    @GetMapping(value = {"/users/all"})
    public ModelAndView getAllUsers(HttpSession session) {
        ModelAndView model = new ModelAndView();
        if (session.getAttribute("userId") == null || session.getAttribute("userId").toString().equals("")) {
            model.setViewName( "redirect:/index" );
        } else {
            if ((Integer) session.getAttribute("roleId") != adminRole) {
                model.setViewName( "redirect:/home" );
            } else {
                UserLoginService service = new UserLoginService();
                model.addObject("uid", session.getAttribute("userId").toString());
                model.addObject( "role", session.getAttribute("roleId").toString());
                model.addObject( "email", session.getAttribute("email").toString());
                model.addObject( "users", service.getAllUsers());
                model.setViewName( "users/list" );
            }
        }
        return model;
    }

    @RequestMapping(value = {"/user/edit"}, method = RequestMethod.POST)
    public ModelAndView editUser(HttpSession session, @RequestParam int roleId, @RequestParam String editUserId) {
        ModelAndView model = new ModelAndView();
        if (session.getAttribute("userId") == null || session.getAttribute("userId").toString().equals("")) {
            model.setViewName( "login" );
        } else {
            UserLoginService service = new UserLoginService();
            User user = service.getUserById(editUserId);
            if (user != null && (Integer) session.getAttribute("roleId") == adminRole) {
                user.setRoleId(roleId);
                service.editUser(user);
            }
            model.setViewName( "users/list" );
        }
        return model;
    }

    @RequestMapping(value = {"/user/delete"}, method = RequestMethod.POST)
    public ModelAndView deleteUser(HttpSession session, @RequestParam String userId) {
        ModelAndView model = new ModelAndView();
        if (session.getAttribute("userId") == null || session.getAttribute("userId").toString().equals("")) {
            model.setViewName( "login" );
        } else {
            UserLoginService service = new UserLoginService();
            User user = service.getUserById(userId);
            if (user != null && (Integer) session.getAttribute("roleId") == adminRole &&
                    !session.getAttribute("userId").toString().equals(user.getId())) {
                service.deleteUser(userId);
            }
            model.setViewName( "users/list" );
        }
        return model;
    }
}
