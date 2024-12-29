package com.mycompany.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/users")
    public String showUserList(Model model){
        List<User> listusers = service.listAll();
        model.addAttribute("listusers",listusers);
        return "users";
    }

    @GetMapping("/users/new")
    public String showNewForm(Model model){
        model.addAttribute("pagetitle","Add new User");
        model.addAttribute("user",new User());

        return "user_form";
    }

    @PostMapping("/users/save")
    public  String saveUser(User user , RedirectAttributes ra){
        service.save(user);
        ra.addFlashAttribute("message"," User saved successfully");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model,RedirectAttributes ra){
        try {
           User user= service.get(id);
            model.addAttribute("pagetitle","Edit User (ID: "+id+")");
           model.addAttribute("user",user);
           return "user_form";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message"," User saved successfully");
            return "redirect:/users";
        }

    }


    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Integer id,RedirectAttributes ra){
        try {
            service.delete(id);
            ra.addFlashAttribute("message","User id"+id+" deleted successfully");
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message",e.getMessage());

        }
        return "redirect:/users";
    }
}
