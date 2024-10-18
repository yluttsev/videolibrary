package ru.luttsev.videolibrary.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.luttsev.videolibrary.entity.Actor;
import ru.luttsev.videolibrary.entity.Comment;
import ru.luttsev.videolibrary.entity.Country;
import ru.luttsev.videolibrary.entity.Director;
import ru.luttsev.videolibrary.entity.Genre;
import ru.luttsev.videolibrary.entity.Movie;
import ru.luttsev.videolibrary.entity.User;

public final class HibernateConfiguration {

    private static SessionFactory sessionFactory;

    static {
        initialize();
    }

    private HibernateConfiguration() {
    }

    private static void initialize() {
        Configuration hibernateConfiguration = new Configuration();
        sessionFactory = hibernateConfiguration
                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Comment.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Director.class)
                .addAnnotatedClass(Genre.class)
                .addAnnotatedClass(Movie.class)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
