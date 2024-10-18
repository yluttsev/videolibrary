package ru.luttsev.videolibrary.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.luttsev.videolibrary.config.HibernateConfiguration;
import ru.luttsev.videolibrary.dao.CrudDao;
import ru.luttsev.videolibrary.entity.User;

import java.util.List;

public class UserDao implements CrudDao<User, Long> {

    private static final String GET_ALL_USERS_HQL = "from User";

    private static volatile UserDao instance;
    private final SessionFactory sessionFactory;

    private UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static UserDao getInstance() {
        if (instance == null) {
            synchronized (UserDao.class) {
                if (instance == null) {
                    instance = new UserDao(HibernateConfiguration.getSessionFactory());
                }
            }
        }
        return instance;
    }

    @Override
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(GET_ALL_USERS_HQL).list();
        }
    }

    @Override
    public User getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public void save(User user) {
        try (Session session = sessionFactory.openSession()) {
            if (user.getId() == null) {
                session.persist(user);
            } else {
                session.merge(user);
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = this.getById(id);
            session.remove(user);
        }
    }
}
