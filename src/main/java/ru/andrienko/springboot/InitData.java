package ru.andrienko.springboot;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.andrienko.springboot.dao.AuthorityDAO;
import ru.andrienko.springboot.entities.Authority;
import ru.andrienko.springboot.entities.User;
import ru.andrienko.springboot.exceptions.DBException;
import ru.andrienko.springboot.service.UserService;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class InitData {
    private Faker faker = new Faker(new Locale("ru"));

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityDAO authorityDAO;

    @PostConstruct
    public void initData() throws DBException {
        Authority auth_user = new Authority("ROLE_USER");
        Authority auth_admin = new Authority("ROLE_ADMIN");
        authorityDAO.insertRole(auth_admin);
        authorityDAO.insertRole(auth_user);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(auth_admin);
        userService.addUser(new User(faker.funnyName().name(), "user", authorities));
        userService.addUser(new User(faker.funnyName().name(), "user", authorities));


    }
}
