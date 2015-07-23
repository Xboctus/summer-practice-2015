package com.threeSergei.storage.controller;

import com.threeSergei.storage.model.StoreEntity;
import com.threeSergei.storage.model.UserEntity;
import com.threeSergei.storage.service.StoreService;
import com.threeSergei.storage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

/**
 * Created by sergej on 23.07.15.
 */
@Controller
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private StoreService storeService;

    private String md5(String data) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(data.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    private String generateHash(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPRQSTUVWXYZ0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for( int i = 0; i < length; i++ )
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return md5(sb.toString());
    }

    @RequestMapping(value = "/auth")
    public String auth(Model model) {
        model.addAttribute("user", new UserEntity());
        return "auth/index";
    }

    @RequestMapping(value = "/authAction", method = RequestMethod.POST)
    public String authAction(@ModelAttribute("user") UserEntity user, HttpSession httpSession) {
        String login = user.getLogin();
        String pass = md5(md5(user.getPassword()));

        if ((user = userService.find(login, pass)) != null) {
            String hash = generateHash(10);
            user.setHash(hash);
            userService.update(user);
            httpSession.setAttribute("id", user.getId());
            httpSession.setAttribute("hash", hash);
            httpSession.setMaxInactiveInterval(60*60*24);
            return "redirect:/";
        }
        return "redirect:/auth";
    }

    @RequestMapping(value = "/registration")
    public String registration(Model model) {
        model.addAttribute("user", new UserEntity());
        return "auth/registration";
    }

    @RequestMapping(value = "/registrationAction", method = RequestMethod.POST)
    public String registrationAction(@ModelAttribute("user") UserEntity user) {
        user.setRole((byte)1);
        String pass = md5(md5(user.getPassword()));
        user.setPassword(pass);

        String name = md5(user.getLogin());
        StoreEntity userStore = new StoreEntity();
        userStore.setName(name);
        userStore.setType((byte)1);

        String root = System.getProperty("user.dir");
        File dir = new File(root + File.separator + "users" + File.separator + name);
        dir.mkdir();

        userService.add(user);
        userStore.setUserId(user.getId());
        storeService.add(userStore);

        user.setStoreId(userStore.getId());
        userService.update(user);

        return "redirect:/auth";
    }
}
