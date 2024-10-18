package ru.luttsev.videolibrary.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.luttsev.videolibrary.config.HibernateConfiguration;
import ru.luttsev.videolibrary.dao.CrudDao;
import ru.luttsev.videolibrary.entity.Director;

import java.util.List;

public class DirectorDao implements CrudDao<Director, Long> {

    private static final String GET_ALL_DIRECTORS_HQL = "from Director";

    private static volatile DirectorDao instance;
    private final SessionFactory sessionFactory;

    private DirectorDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DirectorDao getInstance() {
        if (instance == null) {
            synchronized (DirectorDao.class) {
                if (instance == null) {
                    instance = new DirectorDao(HibernateConfiguration.getSessionFactory());
                }
            }
        }
        return instance;
    }

    @Override
    public List<Director> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(GET_ALL_DIRECTORS_HQL, Director.class).list();
        }
    }

    @Override
    public Director getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Director.class, id);
        }
    }

    @Override
    public void save(Director director) {
        try (Session session = sessionFactory.openSession()) {
            if (director.getId() == null) {
                session.persist(director);
            } else {
                session.merge(director);
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Director director = this.getById(id);
            session.remove(director);
        }
    }
}
