package com.BarTender.controllers;

import com.BarTender.models.Drink;
import com.BarTender.services.DrinkOperationsService;
import com.BarTender.utils.AppConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class DrinkController {
    private int managerRole = AppConstants.RoleConstants.MANAGER;
    @RequestMapping(value = {"/drink/create"}, method = RequestMethod.POST)
    public ModelAndView createDrink(HttpSession session, @RequestParam String name,
        @RequestParam String imageUrl, @RequestParam String barId,
        @RequestParam String description, @RequestParam String amount,
        @RequestParam String volume, @RequestParam String price
    ) {
        ModelAndView model = new ModelAndView();
        if (session.getAttribute("userId") == null || session.getAttribute("userId").toString().equals("") ||
                (Integer) session.getAttribute("roleId") != managerRole) {
            model.setViewName( "redirect:/index");
        } else {
            DrinkOperationsService service = new DrinkOperationsService();
            Drink drink = new Drink();
            String id = String.valueOf(Objects.hash(barId, session.getAttribute("userId").toString(), name));
            drink.setId(id);
            drink.setAlcVolume(Double.parseDouble(volume));
            drink.setAmount(Double.parseDouble(amount));
            drink.setPrice(Double.parseDouble(price));
            drink.setName(name);
            drink.setBarId(barId);
            drink.setImage(imageUrl);
            drink.setDescription(description);
            service.addDrink(drink);
            model.setViewName("home");
        }
        return model;
    }
}
