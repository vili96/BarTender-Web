package com.BarTender.controllers;

import com.BarTender.models.User;
import com.BarTender.services.BarOperationsService;
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
public class LoginController {
    private int managerRole = AppConstants.RoleConstants.MANAGER;
    private int adminRole = AppConstants.RoleConstants.ADMIN;
    private int userRole = AppConstants.RoleConstants.USER;
    @GetMapping(value = {"/","/index"})
    public ModelAndView index() {
        ModelAndView model = new ModelAndView();
        model.setViewName( "login" );
        return model;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public ModelAndView login(HttpSession session, @RequestParam String id, @RequestParam String email) {
        ModelAndView model = new ModelAndView();
        UserLoginService uService = new UserLoginService();
        User existingUser = uService.getUserById(id);
        int roleId;
        if (existingUser == null) {
            User user = new User();
            user.setId(id);
            user.setEmail(email);
            user.setRoleId(managerRole);
            roleId = managerRole;
            uService.addUser(user);
        } else {
            roleId = existingUser.getRoleId();
        }

        if (roleId == userRole) {
            model.setViewName("login");
        } else {
            session.setMaxInactiveInterval(1000000);
            session.setAttribute("userId", id);
            session.setAttribute("roleId", roleId);
            model.setViewName("home");
            model.addObject("session", session);
        }
        return model;
    }

    @RequestMapping(value = {"/logout"} , method= RequestMethod.GET)
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        ModelAndView model = new ModelAndView( );
        model.setViewName( "redirect:/index" );
        return model;
    }

    @GetMapping(value = {"/home"})
    public ModelAndView home(HttpSession session) {
        ModelAndView model = new ModelAndView();
        if (session.getAttribute("userId") == null || session.getAttribute("userId").toString().equals("")) {
            model.setViewName( "login" );
        } else {
            BarOperationsService barService = new BarOperationsService();
            if ((Integer) session.getAttribute("roleId") == adminRole) {
                UserLoginService userLoginService = new UserLoginService();
                model.addObject("managers", userLoginService.getAllManagers());
                model.addObject("bars", barService.getAllBars());
                model.addObject("role", String.valueOf(adminRole));
            } else {
                model.addObject("ownBars", barService.getAllCurrentUserBars(session.getAttribute("userId").toString()));
                model.addObject("role", String.valueOf(managerRole));
            }
            model.setViewName( "home" );
            model.addObject( "uid", session.getAttribute("userId").toString());
        }
        return model;
    }
}
