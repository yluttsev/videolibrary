package ru.luttsev.videolibrary.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.luttsev.videolibrary.config.HibernateConfiguration;
import ru.luttsev.videolibrary.dao.CrudDao;
import ru.luttsev.videolibrary.entity.Country;

import java.util.List;

public class CountryDao implements CrudDao<Country, String> {

    private static final String GET_ALL_COUNTRIES_HQL = "from Country";

    private static volatile CountryDao instance;
    private final SessionFactory sessionFactory;

    private CountryDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CountryDao getInstance() {
        if (instance == null) {
            synchronized (CountryDao.class) {
                if (instance == null) {
                    instance = new CountryDao(HibernateConfiguration.getSessionFactory());
                }
            }
        }
        return instance;
    }

    @Override
    public List<Country> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(GET_ALL_COUNTRIES_HQL, Country.class).list();
        }
    }

    @Override
    public Country getById(String id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Country.class, id);
        }
    }

    @Override
    public void save(Country country) {
        try (Session session = sessionFactory.openSession()) {
            if (country.getId() == null) {
                session.persist(country);
            } else {
                session.merge(country);
            }
        }
    }

    @Override
    public void deleteById(String id) {
        try (Session session = sessionFactory.openSession()) {
            Country country = this.getById(id);
            session.remove(country);
        }
    }
}
