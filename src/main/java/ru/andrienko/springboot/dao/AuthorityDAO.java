package ru.andrienko.springboot.dao;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.andrienko.springboot.entities.Authority;

public class AuthorityDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public Authority getAuthByRole(String role) throws HibernateException {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Authority WHERE role='" + role + "'");
        Authority authority = (Authority) query.list().get(0);
        session.close();
        return authority;
    }
}