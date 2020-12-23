package ru.andrienko.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;


import org.springframework.web.bind.annotation.PostMapping;
import ru.andrienko.springboot.dao.UsersDAO;
import ru.andrienko.springboot.entities.User;
import ru.andrienko.springboot.exceptions.DBException;
import ru.andrienko.springboot.service.UserService;


@Controller

public class UsersController {
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private UserService userService;

    @Autowired
    public UsersController(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    //таблица всех пользователей
    @GetMapping("/allUsers")
    public String getAllUsers(Model model) throws DBException {
        model.addAttribute("users", userService.getAllUsers());
        return "allUsers";
    }

    //создание нового пользователя
    @GetMapping("/newUser")
    public String newUser(@ModelAttribute("user") User user) {
        return "newUser";
    }

    @PostMapping("/newUser")
    public String create(@ModelAttribute("user") User user,
                         BindingResult bindingResult) throws DBException {
        if (bindingResult.hasErrors())
            return "newUser";
        userService.addUser(user);
        return "redirect:/allUsers";
    }

//    удаление пользователя
    @GetMapping("/deleteUser/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/allUsers";
    }

//редактирование пользователя
    @GetMapping("/editUser/{id}")
    public String edit(Model model, @PathVariable("id") int id) throws DBException {
        model.addAttribute("user", userService.getUserById(id));
        return "editUser";
    }

    @PostMapping("/editUser")
    public String update(@ModelAttribute("user") User user) {
        userService.editUser(user);
        return "redirect:/allUsers";
    }
}