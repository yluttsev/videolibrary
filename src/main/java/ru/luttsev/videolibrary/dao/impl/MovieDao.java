package ru.luttsev.videolibrary.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.luttsev.videolibrary.config.HibernateConfiguration;
import ru.luttsev.videolibrary.dao.CrudDao;
import ru.luttsev.videolibrary.entity.Movie;

import java.util.List;

public class MovieDao implements CrudDao<Movie, Long> {

    private static final String GET_ALL_MOVIES_HQL = "from Movie";

    private static volatile MovieDao instance;
    private final SessionFactory sessionFactory;

    private MovieDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static MovieDao getInstance() {
        if (instance == null) {
            synchronized (MovieDao.class) {
                if (instance == null) {
                    instance = new MovieDao(HibernateConfiguration.getSessionFactory());
                }
            }
        }
        return instance;
    }

    @Override
    public List<Movie> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(GET_ALL_MOVIES_HQL, Movie.class).list();
        }
    }

    @Override
    public Movie getById(Long id) {
        return sessionFactory.openSession().get(Movie.class, id);
    }

    @Override
    public void save(Movie movie) {
        try (Session session = sessionFactory.openSession()) {
            if (movie.getId() == null) {
                session.persist(movie);
            } else {
                session.merge(movie);
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Movie movie = this.getById(id);
            session.remove(movie);
        }
    }
}
