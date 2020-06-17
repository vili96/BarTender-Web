package com.BarTender.controllers;

import com.BarTender.models.Bar;
import com.BarTender.services.BarOperationsService;
import com.BarTender.utils.AppConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class BarController {
    private int adminRole = AppConstants.RoleConstants.ADMIN;
    @RequestMapping(value = {"/bar/create"}, method = RequestMethod.POST)
    public ModelAndView createBar(HttpSession session, @RequestParam String name, @RequestParam String imageUrl, @RequestParam String userId, @RequestParam String address) {
        ModelAndView model = new ModelAndView();
        if (session.getAttribute("userId") == null || session.getAttribute("userId").toString().equals("") ||
            (Integer) session.getAttribute("roleId") != adminRole) {
            model.setViewName( "login" );
        } else {
            BarOperationsService barService = new BarOperationsService();
            String id = String.valueOf(Objects.hash(userId, address));
            Bar bar = new Bar();
            bar.setId(id);
            bar.setName(name);
            bar.setImage(imageUrl);
            bar.setUserId(userId);
            bar.setAddress(address);
            barService.addBar(bar);
            model.setViewName("home");
        }
        return model;
    }
}
