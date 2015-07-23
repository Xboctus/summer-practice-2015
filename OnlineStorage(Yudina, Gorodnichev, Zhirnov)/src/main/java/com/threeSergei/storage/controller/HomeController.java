package com.threeSergei.storage.controller;

import com.threeSergei.storage.model.CommonSpaceEntity;
import com.threeSergei.storage.model.StoreEntity;
import com.threeSergei.storage.model.UserEntity;
import com.threeSergei.storage.service.CommonSpaceService;
import com.threeSergei.storage.service.StoreService;
import com.threeSergei.storage.service.UserService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sergej on 21.07.15.
 */
@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private CommonSpaceService commonSpaceService;

    @RequestMapping(value = {"/", "/index"})
    public String index(Model model) {
        UserEntity current = userService.getCurrent();
        List<StoreEntity> userFiles = storeService.getChildren(current.getStoreId());
        List<StoreEntity> commonSpaces = commonSpaceService.getCommonByUser(current.getId());
        model.addAttribute("files", userFiles);
        model.addAttribute("common", commonSpaces);
        model.addAttribute("user", current);
        model.addAttribute("id", current.getStoreId());
        return "home/index";
    }

    private boolean isUserOwner(UserEntity user, int storeId) {
        StoreEntity store = storeService.get(storeId);
        return store.getUserId() == user.getId();
    }

    private boolean userCanAccess(UserEntity user, int storeId){
        if (isUserOwner(user, storeId)) return true;
        List<StoreEntity> csList = commonSpaceService.getCommonByUser(user.getId());
        for (StoreEntity row : csList) {
            if (row.getId() == storeId)
                return true;
        }
        return false;
    }

    @RequestMapping(value = "/folder/{id}")
    public String showFolder(@PathVariable("id") int id, Model model){
        UserEntity current = userService.getCurrent();
        if (!userCanAccess(current, id)) return "redirect:/";

        List<StoreEntity> userFiles = storeService.getChildren(id);
        model.addAttribute("files", userFiles);
        model.addAttribute("user", current);
        model.addAttribute("id", id);
        return "home/folder";
    }

    @RequestMapping(value = "/folder/{id}/load")
    public String load(@PathVariable("id") int id, Model model) {
        UserEntity current = userService.getCurrent();
        if (!isUserOwner(current, id)) return "redirect:/";

        String url = (current.getStoreId() == id) ? "/" : "/folder/" + id;
        model.addAttribute("id", id);
        model.addAttribute("url", url);
        return "home/load";
    }

    @RequestMapping(value = "loadAction", method = RequestMethod.POST)
    public String loadAction(@RequestParam("id") int id, @RequestParam("file") MultipartFile file, @RequestParam("url") String url) {
        UserEntity current = userService.getCurrent();
        if (!file.isEmpty() && isUserOwner(current, id)) {
            try {
                StoreEntity newStore = new StoreEntity();
                newStore.setName(file.getOriginalFilename());
                newStore.setParentId(id);
                newStore.setUserId(current.getId());
                newStore.setType((byte)0);
                storeService.add(newStore);

                String root = System.getProperty("user.dir");
                String path = storeService.getPath(id);
                byte[] bytes = file.getBytes();
                File newFile = new File(root + File.separator + "users" + File.separator + path + File.separator + file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile));
                stream.write(bytes);
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return "redirect:" + url;
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        UserEntity current = userService.getCurrent();
        if (!isUserOwner(current, id)) return "redirect:/";

        StoreEntity store = storeService.get(id);
        String root = System.getProperty("user.dir");
        String path = storeService.getPath(id);
        File storeFile = new File(root + File.separator + "users" + File.separator + path);
        try {
            Files.deleteIfExists(storeFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = (store.getParentId() == current.getStoreId()) ? "/" : "/folder/" + store.getParentId();
        if (store.getType() == 1) {
            List<StoreEntity> storeList = new ArrayList();
            storeList.add(store);
            for (StoreEntity row : storeList) {
                if (!storeService.getChildren(row.getId()).isEmpty())
                    storeList.addAll(storeService.getChildren(row.getId()));
                storeService.remove(row.getId());
            }
        }
        else
            storeService.remove(store.getId());
        return "redirect:" + url;
    }

    @RequestMapping(value = "/folder/{id}/add")
    public String add(@PathVariable("id") int id, Model model) {
        UserEntity current = userService.getCurrent();
        if (!isUserOwner(current, id)) return "redirect:/";

        model.addAttribute("store", new StoreEntity());
        model.addAttribute("id", id);

        String url = (current.getStoreId() == id) ? "/" : "/folder/" + id;
        model.addAttribute("url", url);
        return "home/add";
    }

    @RequestMapping(value = "/addAction", method=RequestMethod.POST)
    public String createAction(@ModelAttribute("store") StoreEntity store, @RequestParam("url") String url) {
        UserEntity current = userService.getCurrent();
        if (!isUserOwner(current, store.getParentId())) return "redirect:/";

        int parent_id = store.getParentId();
        String name = store.getName();
        store.setUserId(current.getId());
        store.setType((byte) 1);
        if(storeService.isNameUnique(parent_id, name)) {
            String root = System.getProperty("user.dir");
            String path = storeService.getPath(parent_id) + File.separator + store.getName();
            File dir = new File(root + File.separator + "users" + File.separator + path);
            dir.mkdir();
            storeService.add(store);
        }
        return "redirect:" + url;
    }

    @RequestMapping(value = "/share/{id}")
    public String share(@PathVariable("id") int id, Model model) {
        UserEntity current = userService.getCurrent();
        if (!isUserOwner(current, id)) return "redirect:/";

        List<UserEntity> usersList = commonSpaceService.getUsersByCommon(id);
        model.addAttribute("id", id);
        model.addAttribute("users", usersList);
        model.addAttribute("cs", new CommonSpaceEntity());
        return "home/share";
    }

    @RequestMapping(value = "/shareAddAction", method=RequestMethod.POST)
    public String shareAction(@RequestParam("sid") int id, @ModelAttribute("cs") CommonSpaceEntity cs) {
        UserEntity current = userService.getCurrent();
        if (!isUserOwner(current, id)) return "redirect:/";

        if (!commonSpaceService.exist(cs.getUserId(), id)) {
            cs.setStoreId(id);
            commonSpaceService.add(cs);
        }
        return "redirect:/share/" + id;
    }

    @RequestMapping(value = "/share/{id}/delete/{userId}")
    public String shareDelete(@PathVariable("id") int id, @PathVariable("userId") int userId, Model model) {
        UserEntity current = userService.getCurrent();
        if (!isUserOwner(current, id)) return "redirect:/";

        commonSpaceService.remove(id, userId);
        return "redirect:/share/" + id;
    }

    @RequestMapping(value = "/download/{id}")
    public void downloadAction(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserEntity current = userService.getCurrent();
        StoreEntity store = storeService.get(id);
        if (!userCanAccess(current, id) || store.getType() != 0) return;

        ServletContext context = request.getServletContext();

        String root = System.getProperty("user.dir");
        String path = storeService.getPath(id);
        File downloadable = new File(root + File.separator + "users" + File.separator + path);
        FileInputStream inputStream = new FileInputStream(downloadable);

        // get MIME type of the file
        String mimeType = context.getMimeType(root + File.separator + "users" + File.separator + path);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadable.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadable.getName());
        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();
    }
}
