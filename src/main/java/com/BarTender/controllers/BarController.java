package com.BarTender.controllers;

import com.BarTender.models.Bar;
import com.BarTender.services.BarOperationsService;
import com.BarTender.services.DrinkOperationsService;
import com.BarTender.utils.AppConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @RequestMapping(value = {"/bar/edit"}, method = RequestMethod.POST)
    public ModelAndView editBar(HttpSession session, @RequestParam String name, @RequestParam String imageUrl, @RequestParam String userId, @RequestParam String address, @RequestParam String editId) {
        ModelAndView model = new ModelAndView();
        if (session.getAttribute("userId") == null || session.getAttribute("userId").toString().equals("")) {
            model.setViewName( "login" );
        } else {
            BarOperationsService service = new BarOperationsService();
            Bar bar = service.getBarById(editId);
            if (bar != null) {
                bar.setName(name);
                bar.setImage(imageUrl);
                if ((Integer) session.getAttribute("roleId") == adminRole) {
                    bar.setUserId(userId);
                }
                bar.setAddress(address);
                service.editBar(bar);
            }
            model.setViewName( "home" );
        }
        return model;
    }

    @GetMapping(value = {"/bar/dashboard"})
    public ModelAndView previewBar(HttpSession session, @RequestParam String barId) {
        ModelAndView model = new ModelAndView();
        if (session.getAttribute("userId") == null || session.getAttribute("userId").toString().equals("")) {
            model.setViewName( "redirect:/index" );
        } else {
            BarOperationsService barService = new BarOperationsService();
            Bar bar = barService.getBarById(barId);
            if (bar == null || (!bar.getUserId().equals(session.getAttribute("userId").toString()) && (Integer) session.getAttribute("roleId") != adminRole)) {
                model.setViewName( "redirect:/home" );
            } else {
                DrinkOperationsService drinkService = new DrinkOperationsService();
                model.addObject( "uid", session.getAttribute("userId").toString());
                model.addObject( "role", session.getAttribute("roleId").toString());
                model.addObject( "barId", bar.getId());
                model.addObject( "drinks", drinkService.getDrinksByBarId(barId));
                if ((Integer) session.getAttribute("roleId") != adminRole) {
                    model.addObject("ownBars", barService.getAllCurrentUserBars(session.getAttribute("userId").toString()));
                }
                model.setViewName( "bars/dashboard" );
            }
        }
        return model;
    }
}
