package ru.andrienko.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import ru.andrienko.springboot.dao.AuthorityDAO;
import ru.andrienko.springboot.dao.UsersDAO;
import ru.andrienko.springboot.entities.Authority;
import ru.andrienko.springboot.entities.User;
import ru.andrienko.springboot.exceptions.DBException;
import ru.andrienko.springboot.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.sun.org.slf4j.internal.LoggerFactory.getLogger;


@Controller

public class UsersController {
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthorityDAO authorityDAO;

    @Autowired
    public UsersController(UsersDAO usersDAO, AuthorityDAO authorityDAO) {
        this.usersDAO = usersDAO;
        this.authorityDAO = authorityDAO;
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
    public String createUser(@RequestParam("login") String login, @RequestParam("password") String password,
                             @RequestParam(name = "auth_user",required = false) boolean auth_user,
                             @RequestParam(name = "auth_admin",required = false) boolean auth_admin) throws DBException {
        List<Authority> authorities = new ArrayList<>();

        if (auth_user && auth_admin) {
            authorities.add(authorityDAO.getAuthByRole("ROLE_USER"));
            authorities.add(authorityDAO.getAuthByRole("ROLE_ADMIN"));
        } else if (auth_user ) {
            authorities.add(authorityDAO.getAuthByRole("ROLE_USER"));
        } else if (auth_admin) {
            authorities.add(authorityDAO.getAuthByRole("ROLE_ADMIN"));
        }
        User user = new User(login, password, authorities);
        userService.addUser(user);
        return "redirect:/allUsers";
    }

    //регистрация
    @GetMapping("/signUp")
    public String signUp(@ModelAttribute("user") User user) {
        return "signUp";
    }

    @PostMapping("/signUp")
    public String create(@ModelAttribute("user") User user) throws DBException {
        userService.addUser(user);
        return "redirect:/allUsers";
    }

    //авторизация
    @GetMapping("/signIn")
    public String signIn() {
        getLogger(UsersController.class).debug("hui");
        return "signIn";
    }

//    @PostMapping("/signIn")
//    public String signInPost(@RequestParam("login") String login,@RequestParam("password") String password) throws DBException {
//        if (userService.getUserByLogin(login)==null){
//            return "signUp";
//        }
//        return "redirect:/allUsers";
//    }

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