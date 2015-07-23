package com.threeSergei.storage.controller;

import com.threeSergei.storage.model.UserEntity;
import com.threeSergei.storage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by sergej on 16.07.15.
 */
@Controller
@RequestMapping(value = "admin")
public class AdminController {
    @Autowired
    protected UserService userService;

    @RequestMapping(value = {"", "/index"})
    public String index(Model model) {
        UserEntity current = userService.getCurrent();
        List<UserEntity> users = userService.getAll();
        model.addAttribute("users", users);
        model.addAttribute("admin", current);
        return "admin/index";
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.remove(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/change/{id}")
    public String change(Model model, @PathVariable("id") int id) {
        UserEntity user = userService.get(id);
        model.addAttribute("user", user);
        return "admin/change";
    }

    @RequestMapping(value = "/changeAction", method = RequestMethod.POST)
    public String changeAction(@ModelAttribute("user") UserEntity user) {
        UserEntity temp = userService.get(user.getId());
        temp.setRole(user.getRole());
        userService.update(temp);
        return "redirect:/admin";
    }
}
