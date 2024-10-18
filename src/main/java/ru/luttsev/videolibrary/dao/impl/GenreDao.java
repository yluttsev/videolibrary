package ru.luttsev.videolibrary.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.luttsev.videolibrary.config.HibernateConfiguration;
import ru.luttsev.videolibrary.dao.CrudDao;
import ru.luttsev.videolibrary.entity.Genre;

import java.util.List;

public class GenreDao implements CrudDao<Genre, String> {

    private static final String GET_ALL_GENRES_HQL = "from Genre";

    private static volatile GenreDao instance;
    private final SessionFactory sessionFactory;

    private GenreDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static GenreDao getInstance() {
        if (instance == null) {
            synchronized (GenreDao.class) {
                if (instance == null) {
                    instance = new GenreDao(HibernateConfiguration.getSessionFactory());
                }
            }
        }
        return instance;
    }

    @Override
    public List<Genre> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(GET_ALL_GENRES_HQL, Genre.class).list();
        }
    }

    @Override
    public Genre getById(String id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Genre.class, id);
        }
    }

    @Override
    public void save(Genre genre) {
        try (Session session = sessionFactory.openSession()) {
            if (genre.getId() == null) {
                session.persist(genre);
            } else {
                session.merge(genre);
            }
        }
    }

    @Override
    public void deleteById(String id) {
        try (Session session = sessionFactory.openSession()) {
            Genre genre = this.getById(id);
            session.remove(genre);
        }
    }
}
