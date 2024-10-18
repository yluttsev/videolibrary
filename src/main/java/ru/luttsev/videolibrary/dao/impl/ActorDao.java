package ru.luttsev.videolibrary.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.luttsev.videolibrary.config.HibernateConfiguration;
import ru.luttsev.videolibrary.dao.CrudDao;
import ru.luttsev.videolibrary.entity.Actor;

import java.util.List;

public class ActorDao implements CrudDao<Actor, Long> {

    private static final String GET_ALL_ACTORS_HQL = "from Actor";

    private static volatile ActorDao instance;
    private final SessionFactory sessionFactory;

    private ActorDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static ActorDao getInstance() {
        if (instance == null) {
            synchronized (ActorDao.class) {
                if (instance == null) {
                    instance = new ActorDao(HibernateConfiguration.getSessionFactory());
                }
            }
        }
        return instance;
    }

    @Override
    public List<Actor> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(GET_ALL_ACTORS_HQL, Actor.class).list();
        }
    }

    @Override
    public Actor getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Actor.class, id);
        }
    }

    @Override
    public void save(Actor actor) {
        try (Session session = sessionFactory.openSession()) {
            if (actor.getId() == null) {
                session.persist(actor);
            } else {
                session.merge(actor);
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Actor actor = this.getById(id);
            session.remove(actor);
        }
    }
}
