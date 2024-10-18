package ru.luttsev.videolibrary.dao;

import java.util.List;

public interface CrudDao<T, I> {
    List<T> getAll();

    T getById(I id);

    void save(T t);

    void deleteById(I id);
}
