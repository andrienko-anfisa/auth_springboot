package ru.andrienko.springboot.service;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andrienko.springboot.dao.UsersDAO;
import ru.andrienko.springboot.entities.Authority;
import ru.andrienko.springboot.entities.User;
import ru.andrienko.springboot.exceptions.DBException;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    public UsersDAO usersDAO;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void addUser(User user) throws DBException {
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
           usersDAO.insertUser(user);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }


    public List<User> getAllUsers() {
        return usersDAO.getUsers();
    }

    @Transactional
    public void deleteUser(Long id) {
        usersDAO.deleteUser(id);
    }

    @Transactional
    public User editUser(User user) {
        usersDAO.editUser(user);
        return user;
    }


    public User getUserById(long id) throws DBException {
        try {
            return usersDAO.getUser(id);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

//    public User getUserByLogin(String login) throws DBException {
//        try {
//            return usersDAO.getUser(login);
//        } catch (HibernateException e) {
//            throw new DBException(e);
//        }
//    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = usersDAO.getUser(login);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
