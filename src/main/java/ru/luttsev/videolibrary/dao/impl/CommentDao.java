package ru.luttsev.videolibrary.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.luttsev.videolibrary.config.HibernateConfiguration;
import ru.luttsev.videolibrary.dao.CrudDao;
import ru.luttsev.videolibrary.entity.Comment;

import java.util.List;

public class CommentDao implements CrudDao<Comment, Long> {

    private static final String GET_ALL_COMMENTS_HQL = "from Comment";

    private static volatile CommentDao instance;
    private final SessionFactory sessionFactory;

    private CommentDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CommentDao getInstance() {
        if (instance == null) {
            synchronized (CommentDao.class) {
                if (instance == null) {
                    instance = new CommentDao(HibernateConfiguration.getSessionFactory());
                }
            }
        }
        return instance;
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(GET_ALL_COMMENTS_HQL, Comment.class).list();
        }
    }

    @Override
    public Comment getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Comment.class, id);
        }
    }

    @Override
    public void save(Comment comment) {
        try (Session session = sessionFactory.openSession()) {
            if (comment.getId() == null) {
                session.persist(comment);
            } else {
                session.merge(comment);
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Comment comment = this.getById(id);
            session.remove(comment);
        }
    }
}
