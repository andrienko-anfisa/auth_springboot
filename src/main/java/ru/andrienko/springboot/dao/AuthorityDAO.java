package ru.andrienko.springboot.dao;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.andrienko.springboot.entities.Authority;
import ru.andrienko.springboot.entities.User;

@Repository
public class AuthorityDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public Authority getAuthByRole(String role) throws HibernateException {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Authority WHERE authority='" + role + "'");
        Authority authority = (Authority) query.list().get(0);
        session.close();
        return authority;
    }

    public long insertRole(Authority authority) {
        Session session = sessionFactory.openSession();
        long id = (Long) session.save(authority);
        session.close();
        return id;
    }
}