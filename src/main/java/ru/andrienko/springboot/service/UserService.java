package ru.andrienko.springboot.service;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andrienko.springboot.dao.UsersDAO;
import ru.andrienko.springboot.entities.User;
import ru.andrienko.springboot.exceptions.DBException;

import java.util.List;

@Service
public class UserService {
    @Autowired
    public UsersDAO usersDAO;

    @Transactional
    public long addUser(User user) throws DBException {
        try {
            return usersDAO.insertUser(user);
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

    public User getUserByLogin(String login) throws DBException {
        try {
            return usersDAO.getUser(login);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

}
